package kmihaly.mywebshop.view;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
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
import com.vaadin.ui.themes.ValoTheme;
import kmihaly.mywebshop.domain.model.item.Purchase;
import kmihaly.mywebshop.domain.model.item.SelectedItem;
import kmihaly.mywebshop.domain.model.item.Size;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import kmihaly.mywebshop.service.DAOItemService;
import kmihaly.mywebshop.service.DAOPurchaseService;
import kmihaly.mywebshop.service.DAOUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.teemu.ratingstars.RatingStars;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    private Grid.Column<SelectedItem, VerticalLayout> optionsColumn;
    private Grid.Column<SelectedItem, Button> deleteColumn;
    private Grid.Column<SelectedItem, String> quantityColumn;

    private Button selectedItemsButton;
    private Button savedItemsButton;


    @PostConstruct
    void init() {
        if (loggedUser.getUserType().equals(UserType.GUEST)) {
            setSizeFull();
            setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
            VerticalLayout contentForGuest = new VerticalLayout();
            contentForGuest.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
            Label title = new Label("To see your bag you have to log in!", ContentMode.PREFORMATTED);
            title.setStyleName(ValoTheme.LABEL_H1);
            Label text = new Label("You can log in here:");
            text.setStyleName(ValoTheme.LABEL_H2);
            Button logInButton = createButton("LOG IN");
            logInButton.setIcon(VaadinIcons.SIGN_IN);
            logInButton.addClickListener(clickEvent -> getUI().getNavigator().navigateTo(SignUpView.VIEW_NAME));
            contentForGuest.addComponents(title, text, logInButton);
            addComponent(contentForGuest);
        } else if (loggedUser.getUserType().equals(UserType.ADMIN)) {

            VerticalLayout sideBar = new VerticalLayout();
            VerticalLayout content = new VerticalLayout();

            Button usersList = createMenuButton("LIST USERS");
            Button purchasesList = createMenuButton("LIST PURCHASES");

            sideBar.setSizeFull();
            sideBar.addComponent(usersList);
            sideBar.addComponent(purchasesList);

            adminUsers.addColumn(User::getUserType).setCaption("type").setStyleGenerator(e -> "middlealign");
            adminUsers.addColumn(User::getUserName).setCaption("username").setStyleGenerator(e -> "middlealign");
            adminUsers.addColumn(User::getFirstName).setCaption("first name").setStyleGenerator(e -> "middlealign");
            adminUsers.addColumn(User::getLastName).setCaption("last name").setStyleGenerator(e -> "middlealign");
            adminUsers.addColumn(User::getPassword).setCaption("password").setStyleGenerator(e -> "middlealign");
            adminUsers.addColumn(User::getEmail).setCaption("email").setStyleGenerator(e -> "middlealign");
            adminUsers.addColumn(User::getAddress).setCaption("address").setStyleGenerator(e -> "middlealign");

            adminPurchases.addColumn(purchase -> purchase.getUser().getUserName()).setCaption("user").setStyleGenerator(e -> "middlealign");
            adminPurchases.addColumn(Purchase::getItems).setCaption("items").setStyleGenerator(e -> "middlealign");
            adminPurchases.addColumn(Purchase::getItemsPrice).setCaption("purchase price").setStyleGenerator(e -> "middlealign");
            adminPurchases.addColumn(Purchase::getDate).setCaption("date").setStyleGenerator(e -> "middlealign");

            Panel panel = new Panel();
            panel.setSizeFull();
            panel.setContent(listUsersLayout());
            usersList.addClickListener((Button.ClickListener) clickEvent -> panel.setContent(listUsersLayout()));
            purchasesList.addClickListener((Button.ClickListener) clickEvent -> panel.setContent(listPurchasesLayout()));


            content.addComponent(panel);
            setSizeFull();
            addComponent(sideBar);
            addComponent(content);
            setExpandRatio(sideBar, 2);
            setExpandRatio(content, 7);
            setSpacing(false);
            setMargin(false);

        } else {

            VerticalLayout sideBar = new VerticalLayout();
            VerticalLayout content = new VerticalLayout();

            selectedItemsButton = createMenuButton("BAG");
            savedItemsButton = createMenuButton("SAVED ITEMS");
            Button myOrdersButton = createMenuButton("MY ORDERS");

            sideBar.setSizeFull();
            sideBar.addComponents(selectedItemsButton, savedItemsButton, myOrdersButton);
            Panel panel = new Panel();
            panel.setSizeFull();

            selectedItems.setSizeFull();
            selectedItems.addStyleName("yourgrid");
            selectedItems.setHeaderVisible(false);
            selectedItems.setFooterVisible(false);
            selectedItems.setHeightMode(HeightMode.UNDEFINED);
            selectedItems.addComponentColumn(item -> {

                Image i = new Image("Image from file", new FileResource(new File(basePath + item.getItem().getSmallImagePath())));
                i.setWidth("250");
                return i;
            }).setCaption("picture").setStyleGenerator(e -> "middlealign").setWidth(300);
            selectedItems.addColumn(item -> item.getItem().getName()).setCaption("name").setStyleGenerator(s -> "middlealign");
            selectedItems.addColumn(item -> item.getItem().getBrand()).setCaption("brand").setStyleGenerator(s -> "middlealign");
            quantityColumn = selectedItems.addColumn(i -> "quantity : " + i.getQuantity()).setCaption("quantity").setHidden(true).setStyleGenerator(s -> "middlealign");
            selectedItems.setBodyRowHeight(260);
            selectedItems.addColumn(i -> i.getItem().getPrice() + "$").setCaption("price").setStyleGenerator(s -> "middlealign");
            deleteColumn = selectedItems.addComponentColumn(this::deleteItemButton).setCaption("delete").setHidden(true);
            optionsColumn = selectedItems.addComponentColumn(this::buttonsForGridLayout).setCaption("options").setHidden(true);



            userPurchases.setHeaderVisible(false);
            userPurchases.setSizeFull();
            userPurchases.setBodyRowHeight(300);
            userPurchases.setHeightMode(HeightMode.UNDEFINED);
            userPurchases.addComponentColumn(this::myOrdersDataLayout).setWidth(400);
            userPurchases.addComponentColumn(this::myOrdersItemsLayout);

            panel.setContent(selectedItemLayout());
            selectedItemsButton.addClickListener((Button.ClickListener) clickEvent -> panel.setContent(selectedItemLayout()));
            savedItemsButton.addClickListener(clickEvent -> panel.setContent(savedItemLayout()));
            myOrdersButton.addClickListener((Button.ClickListener) clickEvent -> panel.setContent(myOrdersLayout()));

            content.addComponent(panel);
            setSizeFull();
            addComponent(sideBar);
            addComponent(content);
            setExpandRatio(sideBar, 2);
            setExpandRatio(content, 7);
            setSpacing(false);
            setMargin(false);
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }


    private Button createButton(String caption) {
        Button button = new Button(caption);
        button.addStyleNames(ValoTheme.BUTTON_DANGER, "mycaption");
        button.setWidth("240");
        button.setHeight("50");
        return button;
    }

    private Button createMenuButton(String caption) {
        Button button = new Button(caption);
        button.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
        button.setWidth("400");
        button.setHeight("60");
        return button;
    }


    private VerticalLayout listUsersLayout() {
        VerticalLayout listUserLayout = new VerticalLayout();
        listUserLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Label title = new Label("REGISTERED USERS");
        title.setStyleName(ValoTheme.LABEL_H1);

        Button addUser = createButton("ADD USER");
        addUser.setStyleName("addbutton");
        addUser.setIcon(VaadinIcons.USER);
        addUser.addClickListener(clickEvent -> MyUI.getCurrent().addWindow(addUserWindow()));
        Button deleteUser = createButton("DELETE USER");
        deleteUser.setStyleName("deletebutton");
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
            Label label = new Label("Your bag is empty,\n" + "please check the shop menu!", ContentMode.PREFORMATTED);
            label.setStyleName(ValoTheme.LABEL_H2);
            verticalLayout.addComponents(label);
        } else {
            optionsColumn.setHidden(true);
            deleteColumn.setHidden(false);
            quantityColumn.setHidden(false);
            selectedItems.setItems(itemService.findItemsByIsForBag(loggedUser, true));
            selectedItems.setSelectionMode(Grid.SelectionMode.SINGLE);
            Label purchasePrice = new Label("TOTAL TO PAY:  $" + purchaseService.getSelectedItemsPrice(loggedUser), ContentMode.PREFORMATTED);
            purchasePrice.setStyleName(ValoTheme.LABEL_H2);
            Button purchase = purchaseButton(purchasePrice);

            verticalLayout.addComponents(selectedItems, purchasePrice, purchase);

            verticalLayout.setComponentAlignment(purchase, Alignment.BOTTOM_RIGHT);
            verticalLayout.setComponentAlignment(purchasePrice, Alignment.BOTTOM_RIGHT);
        }
        return verticalLayout;
    }

    private VerticalLayout savedItemLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Label title = new Label("SAVED ITEMS");
        title.setStyleName(ValoTheme.LABEL_H1);
        verticalLayout.setSizeFull();
        verticalLayout.addComponents(title);

        if (itemService.findItemsByIsForBag(loggedUser, false).isEmpty()) {
            Label infoText = new Label("You haven't saved any item so far!", ContentMode.PREFORMATTED);
            infoText.setStyleName(ValoTheme.LABEL_H2);
            verticalLayout.addComponent(infoText);
        } else {
            optionsColumn.setHidden(false);
            deleteColumn.setHidden(true);
            quantityColumn.setHidden(true);
            Label text = new Label("These items will be stored for 60 days", ContentMode.PREFORMATTED);
            text.setStyleName(ValoTheme.LABEL_H2);
            selectedItems.setBodyRowHeight(200);
            selectedItems.setItems(itemService.findItemsByIsForBag(loggedUser, false));
            verticalLayout.addComponents(text, selectedItems);
        }


        return verticalLayout;
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

            verticalLayout.addComponents(userPurchases);
            verticalLayout.setMargin(false);
            verticalLayout.setSpacing(false);
        }
        return verticalLayout;
    }


    private Button purchaseButton(Label label) {
        Button button = createButton("CONFIRM PURCHASE");
        button.setStyleName("addbutton");
        button.setWidth("300");
        button.setIcon(VaadinIcons.CHECK_SQUARE);
        button.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        button.addClickListener(clickEvent -> {
            {
                purchaseService.purchaseItemsFromStorage(loggedUser);
                label.setValue("TOTAL TO PAY:  $" + purchaseService.getSelectedItemsPrice(loggedUser));
                MyUI.getCurrent().addWindow(purchaseLayout());
                selectedItemsButton.click();
            }
        });
        return button;
    }

    private Window purchaseLayout() {
        Window window = new Window();
        window.setSizeFull();
        VerticalLayout content = new VerticalLayout();
        content.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Label text = new Label("Transfer to the bank page....", ContentMode.PREFORMATTED);
        text.setStyleName(ValoTheme.LABEL_H2);
        content.addComponent(text);
        window.setContent(content);
        return window;

    }


    private Window addUserWindow() {
        Window window = new Window();
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        Label title = new Label("CREATE NEW ACCOUNT");
        title.setStyleName(ValoTheme.LABEL_H2);

        content.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        TextField userName = addUserWindowTextField("username:");
        TextField firstName = addUserWindowTextField("first name:");
        TextField lastName = addUserWindowTextField("last name:");
        TextField password = addUserWindowTextField("password:");
        TextField email = addUserWindowTextField("email:");
        TextField address = addUserWindowTextField("address:");
        DateField dateField = new DateField("birth date:");
        dateField.setWidth("250");
        dateField.addStyleNames(ValoTheme.TEXTAREA_LARGE, "mystyle");
        binder.forField(userName).withNullRepresentation("").withValidator(str -> str.length() >= 3, "must contain at least 3 characters").bind(User::getUserName, User::setUserName);
        binder.forField(firstName).withNullRepresentation("").withValidator(str -> str.length() >= 3, "must contain at least 3 characters").bind(User::getFirstName, User::setFirstName);
        binder.forField(lastName).withNullRepresentation("").withValidator(str -> str.length() >= 3, "must contain at least 3 characters").bind(User::getLastName, User::setLastName);
        binder.forField(password).withNullRepresentation("").withValidator(str -> str.length() >= 3, "must contain at least 3 characters").bind(User::getPassword, User::setPassword);
        binder.forField(email).withNullRepresentation("").withValidator(str -> str.length() >= 3, "must contain at least 3 characters").bind(User::getEmail, User::setEmail);
        binder.forField(address).withNullRepresentation("").withValidator(str -> str.length() >= 3, "must contain at least 3 characters").bind(User::getAddress, User::setAddress);


        ComboBox<UserType> type = new ComboBox<>("authority:");
        type.setWidth("250");
        type.addStyleNames(ValoTheme.COMBOBOX_LARGE, "mystyle");
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
                    !address.isEmpty() && !dateField.isEmpty() && !password.isEmpty() && !type.isEmpty()) {
                User user = new User(userName.getValue(), firstName.getValue(), lastName.getValue(), email.getValue(),
                        address.getValue(), dateField.getValue().toString(), password.getValue(), type.getValue());
                userService.createUser(user);
            } else {
                Notification.show("Please fill all details to create new account!");
            }

        });

        content.addComponents(title, userName, firstName, lastName, dateField, email, address, password, type, confirmAdd);
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


        verticalLayout.addComponents(nameLabel, image);
        return verticalLayout;
    }


    private Button deleteItemButton(SelectedItem item) {
        Button button = new Button("DELETE");
        button.setStyleName("deletebutton");
        button.setWidth("185");
        button.setHeight("50");
        button.setIcon(VaadinIcons.TRASH);
        button.addClickListener(clickEvent -> {
            purchaseService.deleteItemFromStorage(item, loggedUser);
//            selectedItems.setItems(loggedUser.getSelectedItems());
            if (itemService.findItemsByIsForBag(loggedUser, true).isEmpty()) {
                button.click();
            }
        });
        return button;
    }

    private Button addItemToBag(SelectedItem item) {
        Button button = createButton("ADD TO BAG");
        button.setStyleName("addbutton");
        button.setWidth("185");
        button.setIcon(VaadinIcons.PLUS);
        button.addClickListener(clickEvent -> MyUI.getCurrent().addWindow(addItemToBagWindow(item)));
        return button;
    }

    private VerticalLayout buttonsForGridLayout(SelectedItem item) {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeUndefined();
        verticalLayout.setSpacing(false);
        verticalLayout.setMargin(false);
        verticalLayout.addComponents(addItemToBag(item), deleteItemButton(item));
        return verticalLayout;

    }

    private Window addItemToBagWindow(SelectedItem item) {
        Window window = new Window();
        VerticalLayout content = new VerticalLayout();
        Label title = new Label("Before add, please select!");
        title.setStyleName(ValoTheme.LABEL_H2);
        content.addComponent(title);


        Label sizeLabel = new Label("SIZE: ", ContentMode.PREFORMATTED);
        sizeLabel.setStyleName(ValoTheme.LABEL_H3);
        content.addComponent(sizeLabel);

        ComboBox<String> sizeBox = new ComboBox<>();
        Collection<String> sizes = new ArrayList<>();
        for (Size size : Size.values()) {
            sizes.add(size.toString());
        }
        sizeBox.setItems(sizes);
        sizeBox.setEmptySelectionCaption("Please select");
        sizeBox.setEmptySelectionAllowed(false);
        sizeBox.setStyleName(ValoTheme.COMBOBOX_LARGE);
        sizeBox.setWidth("250");
        content.addComponent(sizeBox);

        Label quantityLabel = new Label("QUANTITY: ", ContentMode.PREFORMATTED);
        quantityLabel.setStyleName(ValoTheme.LABEL_H3);
        content.addComponent(quantityLabel
        );
        ComboBox<Integer> quantityBox = new ComboBox<>();
        List<Integer> collect = IntStream.range(1, 6).boxed().collect(Collectors.toList());
        quantityBox.setItems(collect);
        quantityBox.setEmptySelectionCaption("Please select");
        quantityBox.setEmptySelectionAllowed(false);
        quantityBox.setStyleName(ValoTheme.COMBOBOX_LARGE);
        quantityBox.setWidth("250");
        content.addComponent(quantityBox);

        Button add = createButton("ADD");
        add.setIcon(VaadinIcons.PLUS);
        add.setWidth("250");

        add.addClickListener(clickEvent -> {
            itemService.setItemsForBag(item, loggedUser, quantityBox.getValue());
            savedItemsButton.click();
            window.close();
            Notification.show("You added this item to your bag!");

        });
        content.addComponent(add);
        window.setContent(content);
        window.center();
        window.setModal(true);
        return window;
    }


    private HorizontalLayout myOrdersItemsLayout(Purchase purchase) {

        HorizontalLayout images = new HorizontalLayout();
        images.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        images.setSizeFull();
        purchase.getItems().forEach(item -> images.addComponent(myOrdersContentLayout(item)));

        return images;
    }

    private VerticalLayout myOrdersContentLayout(SelectedItem item) {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(false);
        verticalLayout.setSpacing(false);
        verticalLayout.addComponent(new Image("", new FileResource(new File(basePath + item.getItem().getSmallImagePath()))));
        Label label = new Label("Rate it!");
        label.setStyleName(ValoTheme.LABEL_BOLD);
        verticalLayout.addComponent(label);
        RatingStars ratingStars = new RatingStars();
        ratingStars.setValue(item.getItem().getRate().getValue());
        ratingStars.addValueChangeListener(e -> {
            System.err.println("meghivodtam");
            itemService.rateItem(item.getItem(), e.getValue());
        });

        verticalLayout.addComponents(ratingStars);
        return verticalLayout;
    }

    private VerticalLayout myOrdersDataLayout(Purchase purchase) {

        VerticalLayout labels = new VerticalLayout();
        labels.setMargin(false);
        labels.setSpacing(false);

        Label date = new Label("order date: " + purchase.getDate().toString(), ContentMode.PREFORMATTED);
        date.addStyleNames("labelForOrders");

        Label price = new Label("order price: " + purchase.getItemsPrice() + "$", ContentMode.PREFORMATTED);
        price.addStyleNames("labelForOrders");

        labels.addComponents(date, price);
        return labels;
    }
}





