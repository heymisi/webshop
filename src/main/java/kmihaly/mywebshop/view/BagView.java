package kmihaly.mywebshop.view;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.SingleSelectionModel;
import com.vaadin.ui.themes.ValoTheme;
import kmihaly.mywebshop.domain.model.item.*;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import kmihaly.mywebshop.service.DAOItemService;
import kmihaly.mywebshop.service.DAOPurchaseService;
import kmihaly.mywebshop.service.DAOUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.BuildInfoContributor;

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
    @Autowired
    private DAOItemService itemService;

    private Grid<Purchase> userPurchases = new Grid<>();

    private Grid<Purchase> adminPurchases = new Grid<>();

    private Grid<User> adminUsers = new Grid<>();
    private Grid<SelectedItem> selectedItems = new Grid<>();

    private User loggedUser = ((MyUI) UI.getCurrent()).getUser();

    private String basePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    private Binder<User> binder = new Binder<>();

    private Grid.Column<SelectedItem, VerticalLayout> savedItemLayoutColumn;
    private Grid.Column<SelectedItem, Image> imageColumn;
    private Grid.Column<SelectedItem, String> nameColumn;
    private Grid.Column<SelectedItem, Brand> brandColumn;
    private Grid.Column<SelectedItem, Integer> quantityColumn;

    @PostConstruct
    void init() {
        if (loggedUser.getUserType().equals(UserType.ADMIN)) {

            VerticalLayout sideBar = new VerticalLayout();
            VerticalLayout content = new VerticalLayout();

            Button usersList = createMenuButton("LIST USERS");
            Button purchasesList = createMenuButton("LIST PURCHASES");

            sideBar.setSizeFull();
            sideBar.addComponent(usersList);
            sideBar.addComponent(purchasesList);

            adminUsers.addColumn(User::getUserType).setCaption("type");
            adminUsers.addColumn(User::getUserName).setCaption("username");
            adminUsers.addColumn(User::getFirstName).setCaption("first name");
            adminUsers.addColumn(User::getLastName).setCaption("last name");
            adminUsers.addColumn(User::getPassword).setCaption("password");
            adminUsers.addColumn(User::getEmail).setCaption("email");
            adminUsers.addColumn(User::getAddress).setCaption("address");

            adminPurchases.addColumn(purchase -> purchase.getUser().getUserName()).setCaption("user");
            adminPurchases.addColumn(Purchase::getItems).setCaption("items");
            adminPurchases.addColumn(Purchase::getItemsPrice).setCaption("purchase price");
            adminPurchases.addColumn(Purchase::getDate).setCaption("date");

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

            Button selectedItemsButton = createMenuButton("BAG");
            Button savedItemsButton = createMenuButton("SAVED ITEMS");
            Button accountInformationButton = createMenuButton("ACCOUNT INFORMATION");
            Button myOrdersButton = createMenuButton("MY ORDERS");

            sideBar.setSizeFull();
            sideBar.addComponents(selectedItemsButton, savedItemsButton, accountInformationButton, myOrdersButton);
            Panel panel = new Panel();
            panel.setSizeUndefined();
            panel.setSizeFull();
            selectedItems.setSizeFull();
            selectedItems.setHeightMode(HeightMode.UNDEFINED);
            savedItemLayoutColumn = selectedItems.addComponentColumn(this::savedItemsLayoutForColumn);
            imageColumn = selectedItems.addComponentColumn(item -> {
                Image image = new Image("Image from file", new FileResource(new File(basePath + item.getItem().getSmallImagePath())));
                return image;
            }).setCaption("picture").setWidth(220);
            nameColumn = selectedItems.addColumn(item -> item.getItem().getName()).setCaption("name");
            brandColumn = selectedItems.addColumn(item -> item.getItem().getBrand()).setCaption("brand");
            quantityColumn = selectedItems.addColumn(SelectedItem::getQuantity).setCaption("quantity");
            savedItemLayoutColumn.setHidden(true);
            nameColumn.setHidden(true);
            brandColumn.setHidden(true);
            brandColumn.setHidden(true);
            quantityColumn.setHidden(true);

            userPurchases.addColumn(Purchase::getDate).setCaption("date");
            userPurchases.addColumn(Purchase::getItemsPrice).setCaption("price");
            userPurchases.addColumn(Purchase::getItems).setCaption("items");

            panel.setContent(selectedItemLayout());
            selectedItemsButton.addClickListener((Button.ClickListener) clickEvent -> panel.setContent(selectedItemLayout()));
            savedItemsButton.addClickListener(clickEvent -> panel.setContent(savedItemLayout()));
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
        button.addStyleNames(ValoTheme.BUTTON_DANGER);
        button.setWidth("240");
        button.setHeight("50");
        return button;
    }

    private Button createMenuButton(String caption) {
        Button button = new Button(caption);
        button.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
        button.setWidth("300");
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
        verticalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Label title = new Label("BAG");
        title.setStyleName(ValoTheme.LABEL_H1);
        verticalLayout.addComponent(title);
        verticalLayout.setSizeFull();

        if (itemService.findItemsByIsForBag(loggedUser, true).isEmpty()) {
            Label label = new Label("Your bag is empty," + "please check the shop menu!", ContentMode.PREFORMATTED);
            label.setStyleName(ValoTheme.LABEL_H2);
            verticalLayout.addComponents(label);
        } else {
            brandColumn.setHidden(false);
            quantityColumn.setHidden(false);
            nameColumn.setHidden(false);
            imageColumn.setHidden(false);
            savedItemLayoutColumn.setHidden(true);

            selectedItems.setBodyRowHeight(200);
            selectedItems.setItems(itemService.findItemsByIsForBag(loggedUser, true));
            selectedItems.setSelectionMode(Grid.SelectionMode.SINGLE);
            Label purchasePrice = new Label("TOTAL TO PAY:  $" + purchaseService.getSelectedItemsPrice(loggedUser), ContentMode.PREFORMATTED);
            purchasePrice.setStyleName(ValoTheme.LABEL_H2);
            Button purchase = purchaseButton(purchasePrice);
            Button delete = createButton("DELETE SELECTED");
            delete.addClickListener(clickEvent -> {
                Optional<SelectedItem> items = selectedItems.getSelectionModel().getFirstSelectedItem();
                purchaseService.deleteItemFromStorage(items.get(), loggedUser);
                selectedItems.setItems(loggedUser.getSelectedItems());

            });

            verticalLayout.addComponents(selectedItems, delete, purchasePrice, purchase);

            verticalLayout.setComponentAlignment(purchase, Alignment.BOTTOM_RIGHT);
            verticalLayout.setComponentAlignment(purchasePrice, Alignment.BOTTOM_RIGHT);
            verticalLayout.setComponentAlignment(delete, Alignment.BOTTOM_LEFT);
        }
        return verticalLayout;
    }

    private Button savedItemDeleteButton(SelectedItem item) {
        Button button = createButton("DELETE");
        button.setIcon(VaadinIcons.TRASH);

        return button;
    }

    private Button addItemFromSavedToBagButton(SelectedItem item) {
        Button button = createButton("ADD TO BAG");
        button.setIcon(VaadinIcons.ARROW_FORWARD);
        return button;
    }

    private VerticalLayout savedItemLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Label title = new Label("SAVED ITEMS");
        title.setStyleName(ValoTheme.LABEL_H1);
        verticalLayout.addComponents(title);

        if (itemService.findItemsByIsForBag(loggedUser, false).isEmpty()) {

            Label infoText = new Label("You haven't saved any item so far!");
            infoText.setStyleName(ValoTheme.LABEL_H2);
            verticalLayout.addComponent(infoText);
        } else {
            brandColumn.setHidden(true);
            quantityColumn.setHidden(true);
            nameColumn.setHidden(true);
            imageColumn.setHidden(true);
            savedItemLayoutColumn.setHidden(false);

            Label text = new Label("These items will be stored for 60 days", ContentMode.PREFORMATTED);
            text.setStyleName(ValoTheme.LABEL_H2);
            selectedItems.setBodyRowHeight(800);
            selectedItems.setItems(itemService.findItemsByIsForBag(loggedUser, false));
            verticalLayout.addComponents(text, selectedItems);
        }


        return verticalLayout;
    }

    private VerticalLayout accountInformationLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        Label title = new Label("ACCOUNT INFORMATION");
        Label text = new Label("Feel free to edit any of your details below so your account is totally up to date.", ContentMode.PREFORMATTED);
        text.setStyleName(ValoTheme.LABEL_H2);
        title.setStyleName(ValoTheme.LABEL_H1);

        FormLayout formLayout = new FormLayout();
        TextField firstName = createTextField("FIRST NAME:");
        firstName.setPlaceholder(loggedUser.getFirstName());
        TextField lastName = createTextField("LAST NAME:");
        lastName.setPlaceholder(loggedUser.getLastName());
        TextField email = createTextField("EMAIL ADDRESS:");
        email.setPlaceholder(loggedUser.getEmail());
        TextField address = createTextField("ADDRESS:");
        address.setPlaceholder(loggedUser.getAddress());

        formLayout.addComponents( firstName, lastName, email, address);
        verticalLayout.addComponents(title, text,formLayout);
        verticalLayout.addComponent(saveChangesButton(firstName, lastName, email, address));
        verticalLayout.setSizeFull();
        return verticalLayout;
    }

    private Button saveChangesButton(TextField firstName, TextField lastName, TextField email, TextField address) {
        Button button = new Button("SAVE CHANGES");
        button.setIcon(VaadinIcons.REFRESH);
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
            label.setStyleName(ValoTheme.LABEL_H2);
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


    private Button purchaseButton(Label label) {
        Button button = new Button("CONFIRM PURCHASE");
        button.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        button.setStyleName(ValoTheme.BUTTON_DANGER);
        button.addClickListener(clickEvent -> {
            if (loggedUser.getSelectedItems().isEmpty()) {
                Notification.show("Your bag is empty!");
            } else {
                purchaseService.purchaseItemsFromStorage(loggedUser);
                label.setValue("TOTAL TO PAY:  $" + purchaseService.getSelectedItemsPrice(loggedUser));
                selectedItems.setItems(loggedUser.getSelectedItems());
            }
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
            /*  } else*/
            if (Objects.nonNull(userService.findUserByName(userName.getValue()))) {
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
        window.setModal(true);
        return window;
    }

    private TextField addUserWindowTextField(String name) {
        TextField textField = new TextField(name);
        textField.setWidth("250");
        textField.addStyleNames(ValoTheme.TEXTAREA_LARGE, "mystyle");
        return textField;
    }

    private VerticalLayout savedItemsLayoutForColumn(SelectedItem item) {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        Label nameLabel = new Label(item.getItem().getName());
        nameLabel.setStyleName(ValoTheme.LABEL_H2);
        Image image = new Image("", new FileResource(new File(basePath + item.getItem().getLargeImagePath())));


        verticalLayout.addComponents(nameLabel,image);
        return verticalLayout;
    }

    private TextField createTextField(String caption) {
        TextField textField = new TextField(caption);
        textField.setWidth("220");
        textField.addStyleNames(ValoTheme.TEXTAREA_LARGE, ValoTheme.TEXTFIELD_INLINE_ICON, "mystyle");
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        return textField;
    }

}


