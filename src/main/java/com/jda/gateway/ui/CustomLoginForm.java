package com.jda.gateway.ui;

import java.sql.Timestamp;

import org.vaadin.appfoundation.authentication.SessionHandler;
import org.vaadin.appfoundation.authentication.exceptions.AccountLockedException;
import org.vaadin.appfoundation.authentication.exceptions.InvalidCredentialsException;
import org.vaadin.appfoundation.authentication.util.AuthenticationUtil;
import org.vaadin.appfoundation.authentication.util.UserUtil;

import com.ejt.vaadin.loginform.LoginForm;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class CustomLoginForm extends LoginForm {

  private static final long serialVersionUID = 2680478727592359840L;
  private Label feedbackLabel = new Label();
  private FormLayout loginFormLayout = new FormLayout();
  private VerticalLayout footerLayout = new VerticalLayout();
  
  
  @Override
  protected Component createContent(TextField userNameField, PasswordField passwordField, Button loginButton) {
    final VerticalLayout layout = new VerticalLayout();
    layout.setImmediate(false);
    final int maxHeight = UI.getCurrent().getPage().getBrowserWindowHeight();
    final int maxWidth = UI.getCurrent().getPage().getBrowserWindowWidth();
    layout.setHeight(maxHeight, Unit.PIXELS);
    layout.setWidth(maxWidth, Unit.PIXELS);
    layout.setMargin(false);

    // login form
    loginFormLayout.setImmediate(false);
    loginFormLayout.setWidth("100.0%");
    loginFormLayout.setHeight("100.0%");
    loginFormLayout.setMargin(true);
    loginFormLayout.setSpacing(false);

    userNameField.setCaption("Username:");
    userNameField.setImmediate(false);
    userNameField.setWidth("-1px");
    userNameField.setHeight("-1px");
    userNameField.setRequired(true);
    loginFormLayout.addComponent(userNameField);

    passwordField.setCaption("Password:");
    passwordField.setImmediate(false);
    passwordField.setWidth("-1px");;
    passwordField.setHeight("-1px");
    loginFormLayout.addComponent(passwordField);

    // footer layout
    footerLayout = new VerticalLayout();
    footerLayout.setImmediate(false);
    footerLayout.setWidth("100.0%");
    footerLayout.setHeight("-1px");
    footerLayout.setMargin(true);
    footerLayout.setSpacing(false);

    loginButton.setCaption("Sign In");
    loginButton.setSizeUndefined();
    footerLayout.addComponent(loginButton);
    footerLayout.setComponentAlignment(loginButton, Alignment.TOP_CENTER);

    feedbackLabel.setImmediate(false);
    feedbackLabel.setWidth("100.0%");
    feedbackLabel.setHeight("-1px");;
    feedbackLabel.setValue(" ");
    footerLayout.addComponent(feedbackLabel);
    footerLayout.setComponentAlignment(feedbackLabel,  Alignment.BOTTOM_CENTER);
    
    passwordField.addFocusListener(new FieldEvents.FocusListener() {
      @Override
      public void focus(FocusEvent event) {
        feedbackLabel.setValue(" ");        
      } });

    final Panel outerPanel = buildOuterPanel();
    layout.addComponent(outerPanel);
    layout.setComponentAlignment(outerPanel, Alignment.MIDDLE_CENTER);

    return layout;
  }

  private Panel buildOuterPanel() {
    final Panel outerPanel = new Panel("SMS Gateway Login");
    outerPanel.setImmediate(false);
    outerPanel.setWidth("280px");
    outerPanel.setHeight("180px");
    
    // outerPanelLayout
    final VerticalLayout outerPanelLayout = buildOuterPanelLayout();
    outerPanel.setContent(outerPanelLayout);
    
    return outerPanel;
  }

  private VerticalLayout buildOuterPanelLayout() {
    VerticalLayout outerPanelLayout = new VerticalLayout();
    outerPanelLayout.setImmediate(false);
    outerPanelLayout.setWidth("100.0%");
    outerPanelLayout.setHeight("100.0%");
    outerPanelLayout.setMargin(false);
    outerPanelLayout.setSpacing(true);
    
    // innerPanel
    final Panel innerPanel = buildInnerPanel();
    outerPanelLayout.addComponent(innerPanel);
    outerPanelLayout.setComponentAlignment(innerPanel, Alignment.TOP_CENTER);
    
    // footerLayout
    outerPanelLayout.addComponent(footerLayout);
    outerPanelLayout.setComponentAlignment(footerLayout, Alignment.TOP_CENTER);
    
    return outerPanelLayout;
  }

  private Panel buildInnerPanel() {
    Panel innerPanel = new Panel();
    innerPanel.setStyleName("light");
    innerPanel.setImmediate(false);
    innerPanel.setWidth("275px");
    innerPanel.setHeight("100px");
    
    innerPanel.setContent(loginFormLayout);
    
    return innerPanel;
  }

  @Override
  protected void login(String userName, String password) {
    try {
      AuthenticationUtil.authenticate(userName, password);
      SessionHandler.get().setLastLogin(new Timestamp(System.currentTimeMillis()));
      UserUtil.storeUser(SessionHandler.get());
      ((SMSGateway)UI.getCurrent()).loadProtectedResources();
    }
    catch (InvalidCredentialsException e) {
      feedbackLabel.setValue("Username or password incorrect");
    }
    catch (AccountLockedException e) { 
      feedbackLabel.setValue("The account has been locked");
    }
  }

}