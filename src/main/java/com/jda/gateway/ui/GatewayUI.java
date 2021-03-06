package com.jda.gateway.ui;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.vaadin.appfoundation.authentication.SessionHandler;

import com.jda.gateway.TwilioUtil;
import com.jda.gateway.persistence.Handset;
import com.jda.gateway.persistence.HandsetAction;
import com.jda.gateway.persistence.HandsetLog;
import com.jda.gateway.persistence.HandsetNumber;
import com.jda.gateway.persistence.Person;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbsoluteLayout.ComponentPosition;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.Table.ColumnHeaderMode;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings({ "unchecked", "serial", "rawtypes" })
public class GatewayUI extends CustomComponent {

  /*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

  @AutoGenerated
  private AbsoluteLayout mainLayout;

  @AutoGenerated
  private TabSheet mainTabSheet;

  @AutoGenerated
  private HorizontalSplitPanel horizontalSplitPanel_2;

  @AutoGenerated
  private FormLayout messagesRightLayout;

  @AutoGenerated
  private Label feedback;

  @AutoGenerated
  private Button sendButton;

  @AutoGenerated
  private PopupDateField sendOnDateTime;

  @AutoGenerated
  private OptionGroup sendTime;

  @AutoGenerated
  private TextArea message;

  @AutoGenerated
  private TextField actionArgumentField;

  @AutoGenerated
  private ComboBox actionComboBox;

  @AutoGenerated
  private Label selectedHandsetsLabel;

  @AutoGenerated
  private VerticalLayout messagesLeftLayout;

  @AutoGenerated
  private Table handsetList;

  @AutoGenerated
  private HorizontalSplitPanel horizontalSplitPanel_1;

  @AutoGenerated
  private FormLayout phonesRightLayout;

  @AutoGenerated
  private Label phonesTabFeedback;

  @AutoGenerated
  private Button viewHistoryLogLink;

  @AutoGenerated
  private Button updatePhoneButton;

  @AutoGenerated
  private TextField lastUpdateMessageField;

  @AutoGenerated
  private HorizontalLayout deviceIdContainer;

  @AutoGenerated
  private Button addDeviceButton;

  @AutoGenerated
  private TextField deviceIdField;

  @AutoGenerated
  private TextField simIdField;

  @AutoGenerated
  private TextField phoneNumberField;

  @AutoGenerated
  private HorizontalLayout assignedToContainer;

  @AutoGenerated
  private Button addPersonButton;

  @AutoGenerated
  private ComboBox assignedToComboBox;

  @AutoGenerated
  private Label selectedPhoneLabel;

  @AutoGenerated
  private Table phoneList;

  @AutoGenerated
  private MenuBar menuBar;

  // add person window
  private Window addPersonWindow = null;
  private FormLayout addPersonForm;
  private TextField personGivenNameField;
  private TextField personMiddleNameField;
  private TextField personFamilyNameField;
  private TextField personUserIdField;
  private Button personAddPersonButton;
  private Label personFeedbackLabel;
  private HistoryReport historyReport;

  // add device window
  private Window addDeviceWindow = null;
  private AddDeviceLayout addDeviceLayout = null;

  public static final String HANDSET_PREFIX = "PH-";

  /**
   * Default serial id
   */
  private static final long serialVersionUID = 1L;
  private static final String ACTION_ARGUMENT_SEPARATOR = ":";
  private static final String SIMPLE_SMS = "!simple!";  // action message identifier for simple message

  /**
   * The constructor should first build the main layout, set the composition
   * root and then do any custom initialization.
   * 
   * The constructor will not be automatically regenerated by the visual editor.
   */
  public GatewayUI() {
    buildMainLayout();
    setCompositionRoot(mainLayout);

    // populate menu bar
    populateMenuBar();

    final int maxWidth = UI.getCurrent().getPage().getBrowserWindowWidth();
    final int maxHeight = UI.getCurrent().getPage().getBrowserWindowHeight(); 
    
    setWidth(maxWidth, Unit.PIXELS);
    setHeight(maxHeight, Unit.PIXELS);
    mainLayout.setWidth(maxWidth, Unit.PIXELS);
    mainLayout.setHeight(maxHeight, Unit.PIXELS);
    mainTabSheet.setWidth(maxWidth, Unit.PIXELS);
    mainTabSheet.setHeight(maxHeight, Unit.PIXELS);

    // Modify the auto-generated UI as needed
    populateRadioButtons();

    // set date field resolution
    sendOnDateTime.setResolution(Resolution.MINUTE);

    // set message area to disabled until right action is chosen
    message.setEnabled(false);
    
    // populate left side lists
    populateHandsetLists();

    // populate action combo
    populateHandsetActions();

    // populate assigned to combo
    populateAssignedTo();

    // set split position
    horizontalSplitPanel_1.setSplitPosition(30.0f);
    horizontalSplitPanel_2.setSplitPosition(30.0f);

    // create history report form
    historyReport = new HistoryReport(horizontalSplitPanel_1, phonesRightLayout);
    
    // register all the required event handlers
    wireEventHandlers();
  }

  private Command changePasswordCommand = new Command() {
    public void menuSelected(MenuItem selectedItem) {
      ((SMSGateway)UI.getCurrent()).showChangePasswordForm();
    }
  };

  private Command logoutCommand = new Command() {
    public void menuSelected(MenuItem selectedItem) {
      SessionHandler.logout();
    }
  };
  
  private void populateMenuBar() {
    String name;
    if (SessionHandler.get() != null && SessionHandler.get().getName() != null) {
      name = SessionHandler.get().getName();
    }
    else {
      name = "-User-";
    }
    final MenuBar.MenuItem user = menuBar.addItem(name, null);
    user.addItem("Change Password", changePasswordCommand);
    user.addItem("Logout", logoutCommand);

    // reposition the menu bar so it's right-aligned
    ComponentPosition position = mainLayout.getPosition(menuBar);
    int width = name.length() * 5;
    position.setCSSString(String.format("top:20.0px;right:%d.0px", width));
    mainLayout.setPosition(menuBar, position);
  }

  private void populateRadioButtons() {
    sendTime.addItem("Send Now");
    sendTime.addItem("Send On");
  }

  public void populateHandsetLists() {
    JPAContainer<Handset> handsets = JPAContainerFactory.make(Handset.class, SMSGateway.PERSISTENCE_UNIT);
//    JPAContainer<HandsetNumber> phones = JPAContainerFactory.make(HandsetNumber.class, SMSGateway.PERSISTENCE_UNIT);

    // Set up sorting if the natural order is not appropriate
    // phones.sort(new String[] { "number" }, new boolean[] { false });

    // Bind containers to components
    phoneList.setContainerDataSource(handsets);
    handsetList.setContainerDataSource(handsets);

    // phones tab - phone list
    phoneList.setSizeFull();
    phoneList.setMultiSelect(false);
    phoneList.setSelectable(true);
    phoneList.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
    phoneList.setVisibleColumns("html");
    phoneList.setNullSelectionAllowed(false);

    // messages tab - handset list
    handsetList.setSizeFull();
    handsetList.setMultiSelect(true);
    handsetList.setSelectable(true);
    handsetList.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
    handsetList.setVisibleColumns("html");
    handsetList.setNullSelectionAllowed(true);
    
    // override html column with a component, sorting as by the raw html field
    final ColumnGenerator listColumnGenerator = new ColumnGenerator() {
      public Component generateCell(final Table source, final Object itemId, final Object columnId) {
        final JPAContainerItem<Handset> item = (JPAContainerItem<Handset>) source.getItem(itemId);
        final Handset handset = item.getEntity();
        final String html = handset.getHtml();
        final Label label = new Label(html, ContentMode.HTML);
        label.setSizeUndefined();
        label.setReadOnly(true);

        final Embedded embedded = new Embedded("", new ThemeResource("icons/" + handset.getStatus() + ".png"));
        embedded.setWidth("32px");
        embedded.setReadOnly(true);

        final HorizontalLayout cellLayout = new HorizontalLayout();
        cellLayout.addComponent(label);
        cellLayout.addComponent(embedded);
        cellLayout.setComponentAlignment(label, Alignment.BOTTOM_LEFT);
        cellLayout.setComponentAlignment(embedded, Alignment.MIDDLE_RIGHT);
        cellLayout.setSizeFull();

        // Forward clicks on the layout as selection in the table
        cellLayout.addLayoutClickListener(new LayoutClickListener() {
          @Override
          public void layoutClick(LayoutClickEvent event) {
            if (source.isMultiSelect()) {
              if (source.isSelected(itemId)) {
                source.unselect(itemId);
              }
              else {
                source.select(itemId);
              }
            }
            else {
              source.select(itemId);
            }
          }
        });

        return cellLayout;
      }
    };
    
    phoneList.addGeneratedColumn("html", listColumnGenerator);
    handsetList.addGeneratedColumn("html", listColumnGenerator);
    
  }

  private void populateHandsetActions() {
    JPAContainer<HandsetAction> actions = JPAContainerFactory.make(HandsetAction.class, SMSGateway.PERSISTENCE_UNIT);

    // Set up sorting if the natural order is not appropriate
    // phones.sort(new String[] { "number" }, new boolean[] { false });

    // Bind jpacontainer to component
    actionComboBox.setContainerDataSource(actions);
    actionComboBox.setNullSelectionAllowed(false);
    actionComboBox.setItemCaptionPropertyId("action");
  }

  private void populateAssignedTo() {
    JPAContainer<Person> assignedTo = JPAContainerFactory.make(Person.class, SMSGateway.PERSISTENCE_UNIT);

    // Bind jpacontainer to component
    assignedToComboBox.setContainerDataSource(assignedTo);
    assignedToComboBox.setNullSelectionAllowed(false);
    assignedToComboBox.setInvalidAllowed(true);
    assignedToComboBox.setFilteringMode(FilteringMode.CONTAINS);
    assignedToComboBox.setItemCaptionMode(ItemCaptionMode.ITEM); // use
                                                                 // Person.toString()
                                                                 // as the
                                                                 // caption
  }

  private void wireEventHandlers() {

    /*
     * phone list click handler
     */
    phoneList.addValueChangeListener(new Property.ValueChangeListener() {

      @Override
      public void valueChange(ValueChangeEvent event) {
        final JPAContainerItem<Handset> item = (JPAContainerItem) phoneList.getItem(phoneList.getValue());
        final Handset handset = item.getEntity();
        if (handset != null) {
          selectedPhoneLabel.setValue(String.format("%s%03d", HANDSET_PREFIX, handset.getId()));
          actionComboBox.setValue(null);
          actionArgumentField.setValue("");
          actionArgumentField.setVisible(false);
          actionArgumentField.setEnabled(false);
          final Person owner = handset.getOwner();
          if (owner == null) {
            assignedToComboBox.setValue(null);
          } else {
            assignedToComboBox.setValue(owner.getId());
          }
          if (handset.getNumber() != null) {
            phoneNumberField.setValue(handset.getPhoneNumber());
            simIdField.setValue(handset.getNumber().getSimId());
          }
          else {
            phoneNumberField.setValue("");
            simIdField.setValue("");
          }
          deviceIdField.setValue(handset.getDeviceId());
          final String lastUpdateMessage = handset.getLastUpdateMessage();
          lastUpdateMessageField.setValue(lastUpdateMessage);
          phonesTabFeedback.setValue(" ");
          
          // populate history report, if visible
          if (historyReport.isVisible()) {
            historyReport.setHandset(handset);
          }
        }
      }

    });

    /*
     * handset list click handler
     */
    handsetList.addValueChangeListener(new Property.ValueChangeListener() {

      @Override
      public void valueChange(ValueChangeEvent event) {
        final Set<Object> itemIds = (Set<Object>) handsetList.getValue();
        StringBuilder label = new StringBuilder();
        boolean first = true;
        for (Object itemId : itemIds) {
          final JPAContainerItem<Handset> item = (JPAContainerItem) handsetList.getItem(itemId);
          final Handset handset = item.getEntity();
          if (!first) {
            label.append(", ");
          }
          label.append(handset.getPhoneNumber());
          first = false;
        }
        selectedHandsetsLabel.setValue(label.toString());
        actionComboBox.setValue(null);
        actionArgumentField.setValue("");
        actionArgumentField.setVisible(false);
        actionArgumentField.setEnabled(false);
      }

    });

    /*
     * action list click handler
     */
    actionComboBox.addValueChangeListener(new Property.ValueChangeListener() {

      @Override
      public void valueChange(ValueChangeEvent event) {
        final JPAContainerItem<HandsetAction> item = (JPAContainerItem) actionComboBox.getItem(actionComboBox.getValue());
        if (item != null && item.getEntity() != null) {
          final HandsetAction action = item.getEntity();
          // see if it's a simple text message
          if (SIMPLE_SMS.equals(action.getMessage())) {
            message.setEnabled(true);
          }
          else {
            message.setEnabled(false);
          }
          if (action.isArgument()) {
            actionArgumentField.setVisible(true);
            actionArgumentField.setEnabled(true);
            if (action.getDefaultValue() != null) {
              actionArgumentField.setValue(action.getDefaultValue());
            }
            else {
              actionArgumentField.setInputPrompt("Extra Argument");
            }
          }
          else {
            actionArgumentField.setVisible(false);
            actionArgumentField.setEnabled(false);
          }
          phonesTabFeedback.setValue(" ");
        }
        else {
          actionArgumentField.setVisible(false);
          actionArgumentField.setEnabled(false);
        }
      }

    });

    /*
     * send button click handler
     */
    sendButton.addClickListener(new Button.ClickListener() {

      @Override
      public void buttonClick(ClickEvent event) {
        final Set<Object> itemIds = (Set<Object>) handsetList.getValue();
        if (itemIds == null || itemIds.size() == 0) {
          return;
        }

        // for now only send to first item.  Need to add API to send to multiple numbers.
        final Object itemId = itemIds.iterator().next();
        final JPAContainerItem<Handset> handsetItem = (JPAContainerItem) handsetList.getItem(itemId);

        // see if we're sending an action to the phone
        if (handsetItem != null) {
          final Handset handset = handsetItem.getEntity();
          final JPAContainerItem<HandsetAction> actionItem = (JPAContainerItem) actionComboBox.getItem(actionComboBox
              .getValue());
          if (actionItem == null) {
            return;
          }
          final HandsetAction action = actionItem.getEntity();
          StringBuilder messageBuilder = new StringBuilder();

          // sending simple text message?
          final boolean isSimpleSMS = SIMPLE_SMS.equals(action.getMessage());
          if (isSimpleSMS) {
            messageBuilder.append(message.getValue());
          }
          else {
            messageBuilder.append(action.getMessage());
            if (action.isArgument()) {
              messageBuilder.append(ACTION_ARGUMENT_SEPARATOR);
              messageBuilder.append(actionArgumentField.getValue());
            }
          }

          try {
            Notification.show("Sending message: " + messageBuilder);
            final String response = TwilioUtil.sendMessage(handset.getPhoneNumber(), messageBuilder.toString());
            feedback.setValue(response);
          } catch (Exception e) {
            Notification.show("Error: " + e.getMessage());
            feedback.setValue("Error occured sending to [" + handset.getPhoneNumber() + "]: " + e.getMessage());
            return;
          }

          // create log entry
          String logMessage;
          if (isSimpleSMS || action.isArgument()) {
            logMessage = String.format("%s [%s]", action.getAction(), isSimpleSMS ? message.getValue() : actionArgumentField.getValue());
          }
          else {
            logMessage = action.getAction();
          }
          createUserActionLogEntry(handsetItem.getEntity(), logMessage);
          historyReport.refreshHistory();  // refresh history
        }
      }

    });

    /*
     * update phone click listener
     */
    updatePhoneButton.addClickListener(new Button.ClickListener() {

      @Override
      public void buttonClick(ClickEvent event) {
        
        final JPAContainerItem<Handset> handsetItem = (JPAContainerItem) phoneList.getItem(phoneList.getValue());
        final JPAContainerItem<Person> person = (JPAContainerItem) assignedToComboBox.getItem(assignedToComboBox
            .getValue());
        if (handsetItem != null) {
          // see if phone number or sim id changed
          boolean changed = false;
          final HandsetNumber handsetNumber = handsetItem.getEntity().getNumber();
          if (handsetNumber == null) {
            if (phoneNumberField.getValue() != null) {
              changed = true;
            }
          }
          else {
            if (!handsetNumber.getPhoneNumber().equals(phoneNumberField.getValue())
                || !handsetNumber.getSimId().equals(simIdField.getValue())) {
              changed = true;
            }
          }
          if (changed) {
            // TODO: make sure phone numbers are unique (database constraint?)
            HandsetNumber hsNumber = findHandsetNumber(phoneNumberField.getValue(), simIdField.getValue());
            if (hsNumber == null) {
              hsNumber = new HandsetNumber(phoneNumberField.getValue(), simIdField.getValue());
            }
            // TODO: make phone-number populate last received number from log
            handsetItem.getItemProperty("number").setValue(hsNumber);
          }
          // see if device id changed
          if (!handsetItem.getEntity().getDeviceId().equals(deviceIdField.getValue())) {
            handsetItem.getEntity().setDeviceId(deviceIdField.getValue());
          }
          if (person != null) {
            // see if owner changed
            Person savedPerson = (Person) handsetItem.getItemProperty("owner").getValue();
            Person currentPerson = person.getEntity();
            if (!currentPerson.getFullName().equalsIgnoreCase(savedPerson == null ? "" : savedPerson.getFullName())) {
              // update owner
              handsetItem.getItemProperty("owner").setValue(person.getEntity());
              final String msg = String.format("Assigned %s%03d to %s", HANDSET_PREFIX, handsetItem.getEntity().getId(),
                  person.getEntity().getFullName());
              phonesTabFeedback.setValue(msg);

              // create log entry
              createUserActionLogEntry(handsetItem.getEntity(), "Assigned to " + person.getEntity().getFullName());
            }
          }
        }
      }

    });

    /*
     * add person click listener
     */
    addPersonButton.addClickListener(new Button.ClickListener() {

      @Override
      public void buttonClick(ClickEvent event) {
        showAddPersonWindow();
      }

    });

    /*
     * add device click listener
     */
    addDeviceButton.addClickListener(new Button.ClickListener() {

      @Override
      public void buttonClick(ClickEvent event) {
        showAddDeviceWindow();
      }

    });

    /*
     * show history click listener
     */
    viewHistoryLogLink.addClickListener(new Button.ClickListener() {

      @Override
      public void buttonClick(ClickEvent event) {
        if (phoneList.getValue() != null) {
          final JPAContainerItem<Handset> item = (JPAContainerItem) phoneList.getItem(phoneList.getValue());
          final Handset handset = item.getEntity();
          historyReport.setHandset(handset);
          horizontalSplitPanel_1.setSecondComponent(historyReport);
        }
      }

    });

  }
  
  private HandsetNumber findHandsetNumber(final String phoneNumber, final String simId) {
    HandsetNumber result = null;
    final EntityManager em = JPAContainerFactory.createEntityManagerForPersistenceUnit(SMSGateway.PERSISTENCE_UNIT);
    TypedQuery<HandsetNumber> query = em.createQuery(
        "SELECT h FROM HandsetNumber h WHERE h.phoneNumber = :number AND h.simId = :id", HandsetNumber.class);
    List<HandsetNumber> list = query.setParameter("number", phoneNumber).setParameter("id",  simId).getResultList();
    if (list != null && list.size() > 0) {
      result = list.get(0); // return first match, if more than one
    }
    return result;
  }

  /**
   * Create a log entry in the handset_log table for a user-driven action
   * @param handset
   * @param message
   */
  private void createUserActionLogEntry(final Handset handset, final String message) {
    HandsetLog logEntry = new HandsetLog(handset, SessionHandler.get(), message);
    // Get an entity manager
    final EntityManager em = JPAContainerFactory
        .createEntityManagerForPersistenceUnit(SMSGateway.PERSISTENCE_UNIT);
    em.getTransaction().begin();
    em.persist(logEntry);
    em.getTransaction().commit();
  }

  private void showAddPersonWindow() {
    if (addPersonWindow == null) {
      addPersonWindow = new Window("Add a Person");
      addPersonForm = new FormLayout();
      addPersonForm.setMargin(true);
      personGivenNameField = new TextField("Given Name:");
      personMiddleNameField = new TextField("Middle Name:");
      personFamilyNameField = new TextField("Family Name:");
      personUserIdField = new TextField("User Id:");
      addPersonForm.addComponent(personGivenNameField);
      addPersonForm.addComponent(personMiddleNameField);
      addPersonForm.addComponent(personFamilyNameField);
      addPersonForm.addComponent(personUserIdField);
      personAddPersonButton = new Button("Add Person");
      personFeedbackLabel = new Label();
      personFeedbackLabel.setSizeFull();
      addPersonForm.addComponent(personAddPersonButton);
      addPersonForm.addComponent(personFeedbackLabel);
      personGivenNameField.focus();
      addPersonWindow.setContent(addPersonForm);
      addPersonWindow.setModal(true);

      // add a button click listener
      personAddPersonButton.addClickListener(new Button.ClickListener() {

        @Override
        public void buttonClick(ClickEvent event) {
          final String givenName = personGivenNameField.getValue();
          final String middleName = personMiddleNameField.getValue();
          final String familyName = personFamilyNameField.getValue();

          if ((givenName == null || givenName.length() < 1) || 
              (familyName == null || familyName.length() < 1)) {
            personAddPersonButton.setComponentError(new UserError("Please enter at least given name and family name"));
            return;
          }

          // Get an entity manager
          final EntityManager em = JPAContainerFactory
              .createEntityManagerForPersistenceUnit(SMSGateway.PERSISTENCE_UNIT);

          // create, populate, and persist the new Person
          em.getTransaction().begin();
          Person p = new Person(familyName, givenName, middleName);
          p.setUserId(personUserIdField.getValue());
          em.persist(p);
          em.getTransaction().commit();

          final JPAContainer container = (JPAContainer) assignedToComboBox.getContainerDataSource();
          container.refresh();

          personGivenNameField.setValue("");
          personMiddleNameField.setValue("");
          personFamilyNameField.setValue("");
          personUserIdField.setValue("");
          personFeedbackLabel.setValue("Added " + p.getFullName() + ".");
        }

      });
    } else {
      personGivenNameField.setValue("");
      personMiddleNameField.setValue("");
      personFamilyNameField.setValue("");
      personUserIdField.setValue("");
      personFeedbackLabel.setValue(null);
      personAddPersonButton.setComponentError(null);
    }

    getUI().addWindow(addPersonWindow);
    addPersonWindow.setWidth("300px");
    addPersonWindow.setHeight("275px");
    addPersonWindow.center();
  }

  private void showAddDeviceWindow() {
    if (addDeviceWindow == null) {
      addDeviceWindow = new Window("Add a Device");
      addDeviceLayout = new AddDeviceLayout();
      addDeviceWindow.setContent(addDeviceLayout);
      addDeviceWindow.setModal(true);
    }
    else {
      if (addDeviceLayout != null) {
        addDeviceLayout.clearValues();
      }
    }
  
    getUI().addWindow(addDeviceWindow);
    addDeviceWindow.setWidth("325px");
    addDeviceWindow.setHeight("275px");
    addDeviceWindow.center();
  }

  @AutoGenerated
  private AbsoluteLayout buildMainLayout() {
    // common part: create layout
    mainLayout = new AbsoluteLayout();
    mainLayout.setImmediate(true);
    mainLayout.setWidth("100%");
    mainLayout.setHeight("100%");
    
    // top-level component properties
    setWidth("100.0%");
    setHeight("100.0%");
    
    // menuBar
    menuBar = new MenuBar();
    menuBar.setImmediate(true);
    menuBar.setWidth("-1px");
    menuBar.setHeight("-1px");
    mainLayout.addComponent(menuBar, "top:20.0px;left:20.0px;");
    
    // mainTabSheet
    mainTabSheet = buildMainTabSheet();
    mainLayout.addComponent(mainTabSheet, "top:45.0px;left:20.0px;");
    
    return mainLayout;
  }

  @AutoGenerated
  private TabSheet buildMainTabSheet() {
    // common part: create layout
    mainTabSheet = new TabSheet();
    mainTabSheet.setImmediate(true);
    mainTabSheet.setWidth("100.0%");
    mainTabSheet.setHeight("100.0%");
    
    // horizontalSplitPanel_1
    horizontalSplitPanel_1 = buildHorizontalSplitPanel_1();
    mainTabSheet.addTab(horizontalSplitPanel_1, "Phones", null);
    
    // horizontalSplitPanel_2
    horizontalSplitPanel_2 = buildHorizontalSplitPanel_2();
    mainTabSheet.addTab(horizontalSplitPanel_2, "Messages", null);
    
    return mainTabSheet;
  }

  @AutoGenerated
  private HorizontalSplitPanel buildHorizontalSplitPanel_1() {
    // common part: create layout
    horizontalSplitPanel_1 = new HorizontalSplitPanel();
    horizontalSplitPanel_1.setImmediate(false);
    horizontalSplitPanel_1.setWidth("100.0%");
    horizontalSplitPanel_1.setHeight("100.0%");
    
    // phoneList
    phoneList = new Table();
    phoneList.setImmediate(true);
    phoneList.setWidth("100.0%");
    phoneList.setHeight("100.0%");
    horizontalSplitPanel_1.addComponent(phoneList);
    
    // phonesRightLayout
    phonesRightLayout = buildPhonesRightLayout();
    horizontalSplitPanel_1.addComponent(phonesRightLayout);
    
    return horizontalSplitPanel_1;
  }

  @AutoGenerated
  private FormLayout buildPhonesRightLayout() {
    // common part: create layout
    phonesRightLayout = new FormLayout();
    phonesRightLayout.setImmediate(false);
    phonesRightLayout.setWidth("-1px");
    phonesRightLayout.setHeight("-1px");
    phonesRightLayout.setMargin(true);
    phonesRightLayout.setSpacing(true);
    
    // selectedPhoneLabel
    selectedPhoneLabel = new Label();
    selectedPhoneLabel.setCaption("Selected:");
    selectedPhoneLabel.setImmediate(false);
    selectedPhoneLabel.setWidth("-1px");
    selectedPhoneLabel.setHeight("25px");
    selectedPhoneLabel.setValue(" ");
    phonesRightLayout.addComponent(selectedPhoneLabel);
    
    // assignedToContainer
    assignedToContainer = buildAssignedToContainer();
    phonesRightLayout.addComponent(assignedToContainer);
    
    // phoneNumberField
    phoneNumberField = new TextField();
    phoneNumberField.setCaption("Phone Number:");
    phoneNumberField.setImmediate(false);
    phoneNumberField.setWidth("200px");
    phoneNumberField.setHeight("25px");
    phonesRightLayout.addComponent(phoneNumberField);
    
    // simIdField
    simIdField = new TextField();
    simIdField.setCaption("Sim ID:");
    simIdField.setImmediate(false);
    simIdField.setWidth("200px");
    simIdField.setHeight("25px");
    phonesRightLayout.addComponent(simIdField);
    phonesRightLayout.setExpandRatio(simIdField, 0.05f);
    
    // deviceIdContainer
    deviceIdContainer = buildDeviceIdContainer();
    phonesRightLayout.addComponent(deviceIdContainer);
    
    // lastUpdateMessageField
    lastUpdateMessageField = new TextField();
    lastUpdateMessageField.setCaption("Last Message:");
    lastUpdateMessageField.setImmediate(false);
    lastUpdateMessageField.setWidth("200px");
    lastUpdateMessageField.setHeight("25px");
    lastUpdateMessageField.setNullRepresentation("(empty)");
    phonesRightLayout.addComponent(lastUpdateMessageField);
    
    // updatePhoneButton
    updatePhoneButton = new Button();
    updatePhoneButton.setCaption("Update Phone");
    updatePhoneButton.setImmediate(true);
    updatePhoneButton.setWidth("-1px");
    updatePhoneButton.setHeight("-1px");
    phonesRightLayout.addComponent(updatePhoneButton);
    phonesRightLayout.setExpandRatio(updatePhoneButton, 0.3f);
    
    // viewHistoryLogLink
    viewHistoryLogLink = new Button();
    viewHistoryLogLink.setStyleName("v-button-link");
    viewHistoryLogLink.setCaption("View History Log");
    viewHistoryLogLink.setImmediate(true);
    viewHistoryLogLink.setWidth("-1px");
    viewHistoryLogLink.setHeight("-1px");
    phonesRightLayout.addComponent(viewHistoryLogLink);
    phonesRightLayout.setExpandRatio(viewHistoryLogLink, 1.0f);
    
    // phonesTabFeedback
    phonesTabFeedback = new Label();
    phonesTabFeedback.setImmediate(false);
    phonesTabFeedback.setWidth("-1px");
    phonesTabFeedback.setHeight("-1px");
    phonesTabFeedback.setValue(" ");
    phonesRightLayout.addComponent(phonesTabFeedback);
    phonesRightLayout.setExpandRatio(phonesTabFeedback, 3.0f);
    phonesRightLayout.setComponentAlignment(phonesTabFeedback, new Alignment(9));
    
    return phonesRightLayout;
  }

  @AutoGenerated
  private HorizontalLayout buildAssignedToContainer() {
    // common part: create layout
    assignedToContainer = new HorizontalLayout();
    assignedToContainer.setCaption("Assigned To:");
    assignedToContainer.setImmediate(false);
    assignedToContainer.setWidth("-1px");
    assignedToContainer.setHeight("-1px");
    assignedToContainer.setMargin(false);
    assignedToContainer.setSpacing(true);
    
    // assignedToComboBox
    assignedToComboBox = new ComboBox();
    assignedToComboBox.setImmediate(false);
    assignedToComboBox.setWidth("-1px");
    assignedToComboBox.setHeight("-1px");
    assignedToContainer.addComponent(assignedToComboBox);
    
    // addPersonButton
    addPersonButton = new Button();
    addPersonButton.setCaption("Add");
    addPersonButton.setImmediate(true);
    addPersonButton.setWidth("-1px");
    addPersonButton.setHeight("-1px");
    assignedToContainer.addComponent(addPersonButton);
    assignedToContainer.setComponentAlignment(addPersonButton, new Alignment(24));
    
    return assignedToContainer;
  }

  @AutoGenerated
  private HorizontalLayout buildDeviceIdContainer() {
    // common part: create layout
    deviceIdContainer = new HorizontalLayout();
    deviceIdContainer.setCaption("Device ID:");
    deviceIdContainer.setImmediate(false);
    deviceIdContainer.setWidth("-1px");
    deviceIdContainer.setHeight("-1px");
    deviceIdContainer.setMargin(false);
    deviceIdContainer.setSpacing(true);
    
    // deviceIdField
    deviceIdField = new TextField();
    deviceIdField.setImmediate(false);
    deviceIdField.setWidth("200px");
    deviceIdField.setHeight("25px");
    deviceIdContainer.addComponent(deviceIdField);
    deviceIdContainer.setComponentAlignment(deviceIdField, new Alignment(9));
    
    // addDeviceButton
    addDeviceButton = new Button();
    addDeviceButton.setCaption("Add");
    addDeviceButton.setImmediate(true);
    addDeviceButton.setWidth("-1px");
    addDeviceButton.setHeight("-1px");
    deviceIdContainer.addComponent(addDeviceButton);
    deviceIdContainer.setComponentAlignment(addDeviceButton, new Alignment(24));
    
    return deviceIdContainer;
  }

  @AutoGenerated
  private HorizontalSplitPanel buildHorizontalSplitPanel_2() {
    // common part: create layout
    horizontalSplitPanel_2 = new HorizontalSplitPanel();
    horizontalSplitPanel_2.setImmediate(false);
    horizontalSplitPanel_2.setWidth("100.0%");
    horizontalSplitPanel_2.setHeight("100.0%");
    
    // messagesLeftLayout
    messagesLeftLayout = buildMessagesLeftLayout();
    horizontalSplitPanel_2.addComponent(messagesLeftLayout);
    
    // messagesRightLayout
    messagesRightLayout = buildMessagesRightLayout();
    horizontalSplitPanel_2.addComponent(messagesRightLayout);
    
    return horizontalSplitPanel_2;
  }

  @AutoGenerated
  private VerticalLayout buildMessagesLeftLayout() {
    // common part: create layout
    messagesLeftLayout = new VerticalLayout();
    messagesLeftLayout.setImmediate(false);
    messagesLeftLayout.setWidth("100.0%");
    messagesLeftLayout.setHeight("100.0%");
    messagesLeftLayout.setMargin(false);
    
    // handsetList
    handsetList = new Table();
    handsetList.setImmediate(true);
    handsetList.setWidth("100.0%");
    handsetList.setHeight("100.0%");
    messagesLeftLayout.addComponent(handsetList);
    
    return messagesLeftLayout;
  }

  @AutoGenerated
  private FormLayout buildMessagesRightLayout() {
    // common part: create layout
    messagesRightLayout = new FormLayout();
    messagesRightLayout.setImmediate(false);
    messagesRightLayout.setWidth("-1px");
    messagesRightLayout.setHeight("-1px");
    messagesRightLayout.setMargin(true);
    messagesRightLayout.setSpacing(true);
    
    // selectedHandsetsLabel
    selectedHandsetsLabel = new Label();
    selectedHandsetsLabel.setCaption("Selected:");
    selectedHandsetsLabel.setImmediate(false);
    selectedHandsetsLabel.setWidth("-1px");
    selectedHandsetsLabel.setHeight("25px");
    selectedHandsetsLabel.setValue(" ");
    messagesRightLayout.addComponent(selectedHandsetsLabel);
    
    // actionComboBox
    actionComboBox = new ComboBox();
    actionComboBox.setCaption("Action:");
    actionComboBox.setImmediate(true);
    actionComboBox.setWidth("200px");
    actionComboBox.setHeight("25px");
    messagesRightLayout.addComponent(actionComboBox);
    
    // actionArgumentField
    actionArgumentField = new TextField();
    actionArgumentField.setEnabled(false);
    actionArgumentField.setImmediate(true);
    actionArgumentField.setVisible(false);
    actionArgumentField.setWidth("200px");
    actionArgumentField.setHeight("25px");
    messagesRightLayout.addComponent(actionArgumentField);
    
    // message
    message = new TextArea();
    message.setCaption("Message:");
    message.setImmediate(false);
    message.setDescription("Enter the message you'd like to send");
    message.setWidth("300px");
    message.setHeight("-1px");
    messagesRightLayout.addComponent(message);
    
    // sendTime
    sendTime = new OptionGroup();
    sendTime.setImmediate(false);
    sendTime.setWidth("140px");
    sendTime.setHeight("-1px");
    messagesRightLayout.addComponent(sendTime);
    
    // sendOnDateTime
    sendOnDateTime = new PopupDateField();
    sendOnDateTime.setImmediate(false);
    sendOnDateTime.setWidth("-1px");
    sendOnDateTime.setHeight("-1px");
    messagesRightLayout.addComponent(sendOnDateTime);
    
    // sendButton
    sendButton = new Button();
    sendButton.setCaption("Send");
    sendButton.setImmediate(true);
    sendButton.setWidth("100px");
    sendButton.setHeight("-1px");
    messagesRightLayout.addComponent(sendButton);
    
    // feedback
    feedback = new Label();
    feedback.setImmediate(true);
    feedback.setWidth("100.0%");
    feedback.setHeight("-1px");
    feedback.setValue(" ");
    messagesRightLayout.addComponent(feedback);
    
    return messagesRightLayout;
  }

}
