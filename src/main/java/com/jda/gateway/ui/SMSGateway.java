package com.jda.gateway.ui;

import javax.servlet.annotation.WebServlet;

import org.vaadin.appfoundation.authentication.LogoutEvent;
import org.vaadin.appfoundation.authentication.LogoutListener;
import org.vaadin.appfoundation.authentication.SessionHandler;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

@Theme("gateway")
@SuppressWarnings("serial")
@PreserveOnRefresh
public class SMSGateway extends UI implements LogoutListener {

  public static final String PERSISTENCE_UNIT = "sms-gateway";

  
  @WebServlet(value = "/*", asyncSupported = true)
  @VaadinServletConfiguration(productionMode = false, ui = SMSGateway.class,
          widgetset = "com.jda.gateway.AppWidgetSet")
  public static class Servlet extends VaadinServlet {
  }

  @Override
  protected void init(VaadinRequest request) {
    SessionHandler.initialize(this);
    SessionHandler.addListener(this);

    try {
      if (FacadeFactory.getFacade() == null) {
        FacadeFactory.registerFacade(PERSISTENCE_UNIT, true);
      }
    } catch (InstantiationException e) {
      Notification.show(e.getMessage());
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      Notification.show(e.getMessage());
      e.printStackTrace();
    }
    showLoginForm();
  }

  protected void showLoginForm() {
    setContent(new CustomLoginForm());
  }

  protected void showChangePasswordForm() {
    setContent(new ChangePasswordForm());
  }

  protected void loadProtectedResources() {
    CustomComponent component = new GatewayUI();
    component.setSizeFull();
    setContent(component);
    setSizeFull();
  }

  @Override
  public void logout(LogoutEvent event) {
    showLoginForm();
  }
}
