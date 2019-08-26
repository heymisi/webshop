package kmihaly.mywebshop.view;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.SingleSelectionModel;
import com.vaadin.ui.themes.ValoTheme;
import kmihaly.mywebshop.domain.model.item.*;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import kmihaly.mywebshop.service.DAOPurchaseService;
import kmihaly.mywebshop.service.DAOUserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@SpringView(name = BagView.VIEW_NAME)
public class BagView extends HorizontalLayout implements View {

    public static final String VIEW_NAME = "bag";
    @Autowired
    private DAOPurchaseService purchaseService;
    @Autowired
    private DAOUserService userService;

    private Grid<Purchase> userPurchases = new Grid<>();

    private Grid<Purchase> adminPurchases = new Grid<>();

    private Grid<User> adminUsers = new Grid<>();
    private Grid<SelectedItem> selectedItems = new Grid<>();

    private User loggedUser = ((MyUI) UI.getCurrent()).getUser();

    private String basePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    private Binder<User> binder = new Binder<>();


    @PostConstruct
    void init() {
        if (loggedUser.getUserType().equals(UserType.GUEST)) {
            Label label = new Label("You have to be logged in to see this page!");
            label.setStyleName(ValoTheme.LABEL_H2);
            Button button = new Button("SIGN IN", clickEvent -> getUI().getNavigator().navigateTo(SignUpView.VIEW_NAME));
            button.setStyleName(ValoTheme.BUTTON_DANGER);
            VerticalLayout verticalLayout = new VerticalLayout(label, button);
            verticalLayout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
            verticalLayout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);
            addComponents(verticalLayout);
            setComponentAlignment(verticalLayout, Alignment.MIDDLE_CENTER);

        } else if (loggedUser.getUserType().equals(UserType.ADMIN)) {

            VerticalLayout sideBar = new VerticalLayout();
            VerticalLayout content = new VerticalLayout();

            Button usersList = createButton("LIST USERS");
            Button purchasesList = createButton("LIST PURCHASES");

            sideBar.setSizeFull();
            sideBar.addComponent(usersList);
            sideBar.addComponent(purchasesList);

            adminUsers.addColumn(user -> user.getUserType()).setCaption("type");
            adminUsers.addColumn(user -> user.getUserName()).setCaption("username");
            adminUsers.addColumn(user -> user.getFirstName()).setCaption("first name");
            adminUsers.addColumn(user -> user.getLastName()).setCaption("last name");
            adminUsers.addColumn(user -> user.getPassword()).setCaption("password");
            adminUsers.addColumn(user -> user.getEmail()).setCaption("email");
            adminUsers.addColumn(user -> user.getAddress()).setCaption("address");

            adminPurchases.addColumn(purchase -> purchase.getUser().getUserName()).setCaption("user");
            adminPurchases.addColumn(purchase -> purchase.getItems()).setCaption("items");
            adminPurchases.addColumn(purchase -> purchase.getItemsPrice()).setCaption("purchase price");
            adminPurchases.addColumn(purchase -> purchase.getDate()).setCaption("date");

            Panel panel = new Panel();
            panel.setSizeUndefined();
            panel.setSizeFull();
            panel.setContent(listPurchasesLayout());
            usersList.addClickListener((Button.ClickListener) clickEvent -> panel.setContent(listUsersLayout()));
            purchasesList.addClickListener((Button.ClickListener) clickEvent -> panel.setContent(listPurchasesLayout()));


            content.addComponent(panel);
            setSizeFull();
            addComponent(sideBar);
            addComponent(content);
            setExpandRatio(sideBar, 1);
            setExpandRatio(content, 4);
            setSpacing(false);
            setMargin(false);

        } else {

            VerticalLayout sideBar = new VerticalLayout();
            VerticalLayout content = new VerticalLayout();

            Button selectedItemsButton = createButton("Selected Items");
            Button accountInformationButton = createButton("Account information");
            Button myOrdersButton = createButton("My orders");

            sideBar.setSizeFull();
            sideBar.addComponents(selectedItemsButton, accountInformationButton, myOrdersButton);

            Panel panel = new Panel();
            panel.setSizeUndefined();
            panel.setSizeFull();
            selectedItems .addComponentColumn(item -> {
                Image image = new Image("Image from file", new FileResource(new File(basePath + item.getItem().getSmallImagePath())));
                return image;
            }).setCaption("picture").setWidth(220);
            selectedItems.addColumn(item -> item.getItem().getName()).setCaption("name");
            selectedItems.addColumn(item -> item.getItem().getBrand()).setCaption("brand");
            selectedItems.addColumn(item -> item.getQuantity()).setCaption("quantity");

            userPurchases.addColumn(purchase -> purchase.getDate()).setCaption("date");
            userPurchases.addColumn(purchase -> purchase.getItemsPrice()).setCaption("price");
            userPurchases.addColumn(purchase -> purchase.getItems()).setCaption("items");

            panel.setContent(selectedItemLayout());
            selectedItemsButton.addClickListener((Button.ClickListener) clickEvent -> panel.setContent(selectedItemLayout()));
            accountInformationButton.addClickListener((Button.ClickListener) clickEvent -> panel.setContent(accountInformationLayout()));
            myOrdersButton.addClickListener((Button.ClickListener) clickEvent -> panel.setContent(myOrdersLayout()));

            content.addComponent(panel);
            setSizeFull();
            addComponent(sideBar);
            addComponent(content);
            setExpandRatio(sideBar, 1);
            setExpandRatio(content, 4);
            setSpacing(false);
            setMargin(false);
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }


    private Button createButton(String caption) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_DANGER);
        button.setWidth("240");
        button.setHeight("50");
        return button;
    }


    private VerticalLayout listUsersLayout() {
        VerticalLayout listUserLayout = new VerticalLayout();
        listUserLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Label title = new Label("REGISTERED USERS");
        title.setStyleName(ValoTheme.LABEL_H1);

        Button addUser = createButton("ADD USER");
        addUser.setIcon(VaadinIcons.USER);
        addUser.addClickListener(clickEvent -> MyUI.getCurrent().addWindow(addUserWindow()));
        Button deleteUser = createButton("DELETE USER");
        deleteUser.setIcon(VaadinIcons.CLOSE);
        adminUsers.setItems(userService.listUsers());
        adminUsers.setSizeFull();
        listUserLayout.addComponents(title, adminUsers, addUser, deleteUser);
        listUserLayout.setComponentAlignment(addUser, Alignment.BOTTOM_LEFT);
        listUserLayout.setComponentAlignment(deleteUser, Alignment.BOTTOM_LEFT);

        return listUserLayout;

    }

    private VerticalLayout listPurchasesLayout() {
        VerticalLayout listUserLayout = new VerticalLayout();
        listUserLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        Label title = new Label("PURCHASES");
        title.setStyleName(ValoTheme.LABEL_H1);
        listUserLayout.addComponent(title);
        if (purchaseService.listPurchases().isEmpty()) {
            Label label = new Label("There was no purchase so far!", ContentMode.PREFORMATTED);
            label.setStyleName(ValoTheme.LABEL_H2);
            listUserLayout.addComponent(label);
        } else {
            adminPurchases.setItems(purchaseService.listPurchases());
            adminPurchases.setSizeFull();
            listUserLayout.addComponents(adminPurchases);
        }
        return listUserLayout;
    }


    private VerticalLayout selectedItemLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        Label title = new Label("SELECTED ITEMS");
        title.setStyleName(ValoTheme.LABEL_H1);
        verticalLayout.addComponent(title);
        verticalLayout.setSizeFull();
        verticalLayout.setComponentAlignment(title, Alignment.MIDDLE_CENTER);

        if (loggedUser.getSelectedItems().isEmpty()) {
            Label label = new Label("You have not put any item to your bag yet\n" + "Please check the shop menu before click here", ContentMode.PREFORMATTED);
            label.setStyleName(ValoTheme.LABEL_H3);
            verticalLayout.addComponents(label);
            verticalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
            verticalLayout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
        } else {
            selectedItems.setSizeFull();
            selectedItems.setBodyRowHeight(200);
            selectedItems.setItems(loggedUser.getSelectedItems());
            selectedItems.setSelectionMode(Grid.SelectionMode.SINGLE);

            Button purchase = purchaseButton();
            Button delete = new Button("DELETE SELECTED");
            delete.addClickListener(clickEvent -> {
                Optional<SelectedItem> items = selectedItems.getSelectionModel().getFirstSelectedItem();
                purchaseService.deleteItemFromStorage(items.get(), loggedUser);
                selectedItems.setItems(loggedUser.getSelectedItems());

            });

            Label purchasePrice = new Label("TOTAL TO PAY:  $" + purchaseService.getSelectedItemsPrice(loggedUser), ContentMode.PREFORMATTED);

            verticalLayout.addComponents(selectedItems, delete, purchasePrice, purchase);

            verticalLayout.setComponentAlignment(purchase, Alignment.BOTTOM_RIGHT);
            verticalLayout.setComponentAlignment(purchasePrice, Alignment.BOTTOM_RIGHT);
            verticalLayout.setComponentAlignment(delete, Alignment.BOTTOM_LEFT);
        }
        return verticalLayout;
    }

    private VerticalLayout accountInformationLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        Label title = new Label("ACCOUNT INFORMATION");
        Label text = new Label("Feel free to edit any of your details below so your account is totally up to date.", ContentMode.PREFORMATTED);

        title.setStyleName(ValoTheme.LABEL_H1);

        TextField firstName = new TextField("FIRST NAME:");
        firstName.setPlaceholder(loggedUser.getFirstName());
        TextField lastName = new TextField("LAST NAME:");
        lastName.setPlaceholder(loggedUser.getLastName());
        TextField email = new TextField("EMAIL ADDRESS:");
        email.setPlaceholder(loggedUser.getEmail());
        TextField address = new TextField("ADDRESS:");
        address.setPlaceholder(loggedUser.getAddress());


        verticalLayout.addComponents(title, text, firstName, lastName, email, address);
        verticalLayout.addComponent(saveChangesButton(firstName, lastName, email, address));
        verticalLayout.setSizeFull();
        return verticalLayout;
    }

    private Button saveChangesButton(TextField firstName, TextField lastName, TextField email, TextField address) {
        Button button = new Button("SAVE CHANGES");
        button.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        button.setStyleName(ValoTheme.BUTTON_DANGER);
        button.addClickListener(clickEvent -> {

            if (!firstName.getValue().isEmpty()) {
                loggedUser.setFirstName(firstName.getValue());
            }
            if (!lastName.getValue().isEmpty()) {
                loggedUser.setLastName(lastName.getValue());
            }
            if (!email.getValue().isEmpty()) {
                loggedUser.setEmail(email.getValue());
            }
            if (!address.getValue().isEmpty()) {
                loggedUser.setAddress(address.getValue());
            }
            if (!firstName.getValue().isEmpty() || !lastName.getValue().isEmpty() || !email.getValue().isEmpty() || !address.getValue().isEmpty()) {
                userService.createUser(loggedUser);
                Notification.show("Successful change");
            } else {
                Notification.show("If you want to change your details your have to fill the below areas");
            }
        });
        return button;
    }

    private VerticalLayout myOrdersLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        Label title = new Label("MY ORDERS");
        title.setStyleName(ValoTheme.LABEL_H1);
        verticalLayout.addComponent(title);
        verticalLayout.setSizeFull();
        verticalLayout.setComponentAlignment(title, Alignment.MIDDLE_CENTER);

        if (purchaseService.listPurchases().isEmpty()) {
            Label label = new Label("There was no previous purchase", ContentMode.PREFORMATTED);
            verticalLayout.addComponent(label);
            label.setStyleName(ValoTheme.LABEL_H3);
            verticalLayout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
        } else {

            userPurchases.setItems(purchaseService.listPurchases());
            userPurchases.setSizeFull();

            verticalLayout.addComponents(userPurchases);
            verticalLayout.setMargin(false);
            verticalLayout.setSpacing(false);
        }
        return verticalLayout;
    }


    private Button purchaseButton() {
        Button button = new Button("CONFIRM PURCHASE");
        button.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        button.setStyleName(ValoTheme.BUTTON_DANGER);
        button.addClickListener(clickEvent -> {
            purchaseService.purchaseItemsFromStorage(loggedUser);
            selectedItems.setItems(loggedUser.getSelectedItems());

        });
        return button;
    }


    private Window addUserWindow() {
        Window window = new Window();
        VerticalLayout content = new VerticalLayout();

        Label title = new Label("CREATE NEW ACCOUNT");
        title.setStyleName(ValoTheme.LABEL_H2);

        content.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        TextField userName = addUserWindowTextField("username:");
        TextField firstName = addUserWindowTextField("first name:");
        TextField lastName = addUserWindowTextField("last name:");
        TextField password = addUserWindowTextField("password:");
        TextField email = addUserWindowTextField("email:");
        TextField address = addUserWindowTextField("address:");
        binder.forField(userName).withNullRepresentation("").withValidator(str -> str.length() >= 3, "must contain at least 3 characters").bind(User::getUserName, User::setUserName);
        binder.forField(firstName).withNullRepresentation("").withValidator(str -> str.length() >= 3, "must contain at least 3 characters").bind(User::getFirstName, User::setFirstName);
        binder.forField(lastName).withNullRepresentation("").withValidator(str -> str.length() >= 3, "must contain at least 3 characters").bind(User::getLastName, User::setLastName);
        binder.forField(password).withNullRepresentation("").withValidator(str -> str.length() >= 3, "must contain at least 3 characters").bind(User::getPassword, User::setPassword);
        binder.forField(email).withNullRepresentation("").withValidator(str -> str.length() >= 3, "must contain at least 3 characters").bind(User::getEmail, User::setEmail);
        binder.forField(address).withNullRepresentation("").withValidator(str -> str.length() >= 3, "must contain at least 3 characters").bind(User::getAddress, User::setAddress);


        ComboBox<UserType> type = new ComboBox<>("authority:");
        type.setWidth("250");
        type.setStyleName(ValoTheme.COMBOBOX_LARGE);
        type.setEmptySelectionAllowed(false);
        ArrayList<UserType> userTypes = new ArrayList<>();
        for (UserType types : UserType.values()) {
            if (!types.toString().equals("Guest")) {
                userTypes.add(types);
            }
        }
        type.setItems(userTypes);

        Button confirmAdd = createButton("CONFIRM ADD");
        confirmAdd.setIcon(VaadinIcons.CHECK);
        confirmAdd.addClickListener(clickEvent -> {
//            if (Objects.nonNull(userService.findUserByEmail(email.getValue()))) {
//                Notification.show("This email has been already used!");
          /*  } else*/ if (Objects.nonNull(userService.findUserByName(userName.getValue()))) {
                Notification.show("This username has been already used!");
            } else if (!userName.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() &&
                    !address.isEmpty() && !password.isEmpty() && !type.isEmpty()) {
                User user = new User(userName.getValue(), firstName.getValue(), lastName.getValue(), email.getValue(),
                        address.getValue(), password.getValue(), type.getValue());
                userService.createUser(user);
            } else {
                Notification.show("Please fill all details to create new account!");
            }

        });

        content.addComponents(title, userName, firstName, lastName, password, email, address, type, confirmAdd);
        window.setContent(content);
        window.center();
        return window;
    }

    private TextField addUserWindowTextField(String name) {
        TextField textField = new TextField(name);
        textField.setWidth("250");
        textField.setStyleName(ValoTheme.TEXTAREA_LARGE);
        return textField;
    }

}


