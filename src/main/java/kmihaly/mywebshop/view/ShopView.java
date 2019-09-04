package kmihaly.mywebshop.view;

import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import kmihaly.mywebshop.domain.model.item.Brand;
import kmihaly.mywebshop.domain.model.item.Genre;
import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.domain.model.item.Type;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import kmihaly.mywebshop.service.DAOItemService;
import kmihaly.mywebshop.service.DAOPurchaseService;
import kmihaly.mywebshop.service.DAOUserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.*;

import static kmihaly.mywebshop.view.MyUI.getCurrent;

@SpringView(name = ShopView.VIEW_NAME)
public class ShopView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "shop";

    @Autowired
    private DAOItemService itemService;
    @Autowired
    private DAOPurchaseService purchaseService;
    @Autowired
    private DAOUserService userService;

    private Grid<Item> itemsForMen = new Grid<>();
    private Grid<Item> itemsForWomen = new Grid<>();

    private int foundItem;

    private String basePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    private User loggedUser = ((MyUI) UI.getCurrent()).getUser();

    private Binder<Item> binder = new Binder<>();


    @PostConstruct
    void init() {
        setSizeFull();
        setUpItems(itemsForMen, Genre.MEN);
        setUpItems(itemsForWomen, Genre.WOMEN);

        HorizontalLayout layoutMen = filter(itemsForMen, Genre.MEN);
        HorizontalLayout layoutWomen = filter(itemsForWomen, Genre.WOMEN);

        layoutMen.setSizeFull();
        layoutWomen.setSizeFull();

        TabSheet tabs = new TabSheet();
        addComponent(tabs);
        VerticalLayout tab1 = new VerticalLayout();
        VerticalLayout tab2 = new VerticalLayout();

        tabs.addTab(tab1, "MEN").setIcon(VaadinIcons.MALE);
        tabs.addTab(tab2, "WOMEN").setIcon(VaadinIcons.FEMALE);
        tabs.setSizeFull();
        tabs.addStyleNames(ValoTheme.TABSHEET_EQUAL_WIDTH_TABS, ValoTheme.TABSHEET_FRAMED, "dirtyTabCaption");
        tab1.addComponents(layoutMen);
        tabs.addSelectedTabChangeListener((TabSheet.SelectedTabChangeListener) selectedTabChangeEvent -> {
            TabSheet tabSheet = selectedTabChangeEvent.getTabSheet();
            Layout tab = (Layout) tabSheet.getSelectedTab();
            String caption = tabSheet.getTab(tab).getCaption();
            tab.removeAllComponents();

            if (caption.equals("MEN")) {
                tab.addComponent(layoutMen);
            } else if (caption.equals("WOMEN")) {
                tab.addComponent(layoutWomen);
            }
        });
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    private HorizontalLayout filter(Grid<Item> items, Genre genreType) {
        VerticalLayout sideBar = new VerticalLayout();
        Label label = new Label("Advanced search");
        label.setStyleName(ValoTheme.LABEL_H2);
        TextField search = new TextField("Search by name");
        search.addStyleNames("mystyle", ValoTheme.TEXTFIELD_LARGE);
        search.setWidth("240");
        search.setPlaceholder("All");
        ComboBox<String> typeFilter = new ComboBox<>("Choose Type");
        typeFilter.addStyleNames("mystyle", ValoTheme.COMBOBOX_LARGE);
        typeFilter.setWidth("240");
        Collection<String> types = new ArrayList<>();
        for (Type type : Type.values()) {
            types.add(type.toString());
        }

        typeFilter.setItems(types);
        typeFilter.setEmptySelectionCaption("All");

        Collection<String> brandTypes = new ArrayList<>();
        for (Brand brand : Brand.values()) {
            brandTypes.add(brand.toString());
        }

        ComboBox<String> brandFilter = new ComboBox<>("Choose Brand");
        brandFilter.addStyleNames("mystyle", ValoTheme.COMBOBOX_LARGE);
        brandFilter.setPlaceholder("Choose Brand");
        brandFilter.setEmptySelectionCaption("All");
        brandFilter.setItems(brandTypes);
        brandFilter.setWidth("240");
        Label price = new Label();

        Slider slider = new Slider("Choose Price Limit", 0, 100);
        slider.addStyleNames("mystyle", ValoTheme.SLIDER_NO_INDICATOR);
        slider.setResolution(0);
        slider.setWidth("240");
        slider.addValueChangeListener((HasValue.ValueChangeEvent<Double> event) -> {
            Double val = event.getValue();
            price.setValue(Math.round(val) + "$");
        });

        Button searchButton = createButton("Search");
        searchButton.setIcon(VaadinIcons.SEARCH);
        searchButton.setWidth("240");
        searchButton.setHeight("45");
        Label title = new Label();
        title.setStyleName(ValoTheme.LABEL_H2);
        if (genreType.equals(Genre.MEN)) {
            title.setValue("MEN CLOTHES");
        } else {
            title.setValue("WOMEN CLOTHES");
        }
        Label rowCount = new Label(itemService.searchByGenre(genreType).size() + " clothes found");
        rowCount.addStyleNames(ValoTheme.LABEL_H3, ValoTheme.LABEL_BOLD);
        searchButton.addClickListener((Button.ClickListener) clickEvent -> {
            List<Item> filteredItems = itemService.multipleSearch(search.getValue(), genreType.toString(), brandFilter.getValue(), typeFilter.getValue(), slider.getValue().intValue());
            foundItem = filteredItems.size();
            rowCount.setValue(foundItem + " clothes found");
            items.setItems(filteredItems);
            if (foundItem == 0) {
                items.setVisible(false);
            } else {
                items.setVisible(true);
            }
        });
        Button addItem = createButton("ADD ITEM");
        addItem.setStyleName("addbutton");
        addItem.setWidth("400");
        addItem.setIcon(VaadinIcons.PLUS);
        addItem.addClickListener(clickEvent -> getCurrent().addWindow(addItemWindow()));
        Button deleteItem = createButton("DELETE ITEM");
        deleteItem.setStyleName("deletebutton");
        deleteItem.setWidth("400");
        deleteItem.setIcon(VaadinIcons.TRASH);
        deleteItem.addClickListener(clickEvent -> {
            Set<Item> item = items.getSelectionModel().getSelectedItems();
            if (item.isEmpty()) {
                Notification.show("To delete an item you have to select one!");
            } else if (itemService.isSelected(item)) {
                Notification.show("You can not delete item that already been used by users!");
            } else {
                getCurrent().addWindow(verificationWindow("Are you sure to delete?", clickEvent1 -> {
                    for (Item i : item) {
                        itemService.deleteItem(i);
                    }
                    items.setItems(itemService.searchByGenre(genreType));
                }));
            }
        });
        if (loggedUser.getUserType().equals(UserType.ADMIN)) {
            items.setSelectionMode(Grid.SelectionMode.MULTI);
        }
        addItem.setVisible(loggedUser.getUserType().equals(UserType.ADMIN));
        deleteItem.setVisible(loggedUser.getUserType().equals(UserType.ADMIN));


        VerticalLayout itemLayout = new VerticalLayout(title, rowCount, items, addItem, deleteItem);
        itemLayout.setComponentAlignment(title, Alignment.TOP_CENTER);
        itemLayout.setComponentAlignment(rowCount, Alignment.TOP_CENTER);
        itemLayout.setSizeFull();

        sideBar.addComponents(label, search, typeFilter, brandFilter, slider, price, searchButton);
        sideBar.setComponentAlignment(price, Alignment.MIDDLE_CENTER);
        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.addComponent(sideBar);
        mainLayout.addComponent(itemLayout);
        mainLayout.setComponentAlignment(itemLayout, Alignment.MIDDLE_CENTER);
        mainLayout.setExpandRatio(sideBar, 2);
        mainLayout.setExpandRatio(itemLayout, 10);
        mainLayout.setSizeFull();

        return mainLayout;
    }

    private void setUpItems(Grid<Item> items, Genre genreType) {
        items.setSizeFull();
        items.setHeightMode(HeightMode.UNDEFINED);
        items.setHeaderVisible(false);
        items.setFooterVisible(false);
        items.addComponentColumn(item -> new Image("Image from file", new FileResource(new File(basePath + item.getSmallImagePath())))).setCaption("picture").setWidth(220);
        items.setItems(itemService.searchByGenre(genreType));
        items.addColumn(Item::getName).setCaption("name").setStyleGenerator(e -> "middlealign");
        items.addColumn(item ->"price: " + item.getPrice() + "$").setCaption("price").setStyleGenerator(e -> "middlealign");
        items.setBodyRowHeight(200);
        items.addComponentColumn(this::itemDetailsButton).setCaption("more info");
        items.setSelectionMode(Grid.SelectionMode.SINGLE);
    }


    private Button createButton(String string) {
        Button button = new Button(string);
        button.setStyleName(ValoTheme.BUTTON_DANGER);
        button.setWidth("180");
        return button;
    }

    private Button itemDetailsButton(Item item) {
        Button button = new Button("MORE INFORMATION");
        button.setStyleName(ValoTheme.BUTTON_DANGER);
        button.setIcon(VaadinIcons.ANGLE_DOWN);
        button.addClickListener(event -> getCurrent().addWindow(new ItemDetails(item, loggedUser, purchaseService, itemService)));
        return button;
    }


    private Window addItemWindow() {
        Window window = new Window();
        window.setModal(true);
        FormLayout content = new FormLayout();
        content.setSizeUndefined();
        content.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        Label title = new Label("ADD NEW ITEM");
        title.addStyleNames(ValoTheme.LABEL_H1,ValoTheme.LABEL_BOLD);
        TextField nameField = createTextField("Name:");
        TextField descriptionField = createTextField("Description:");
        TextField priceField = createTextField("Price($):");
        priceField.addStyleName("my-text");
        TextField availableQuantityField = createTextField("Quantity:");
        availableQuantityField.addStyleName("my-text");
        ComboBox<Type> typeComboBox = new ComboBox<>("Type:");
        typeComboBox.addStyleNames(ValoTheme.COMBOBOX_LARGE, "mystyle");
        typeComboBox.setPlaceholder("Please select");
        typeComboBox.setWidth("400");

        ComboBox<Genre> genreComboBox = new ComboBox<>("Genre:");
        genreComboBox.addStyleNames(ValoTheme.COMBOBOX_LARGE, "mystyle");
        genreComboBox.setPlaceholder("Please select");
        genreComboBox.setWidth("400");

        ComboBox<Brand> brandComboBox = new ComboBox<>("Brand:");
        brandComboBox.addStyleNames(ValoTheme.COMBOBOX_LARGE, "mystyle");
        brandComboBox.setPlaceholder("Please select");
        brandComboBox.setWidth("400");


        binder.forField(nameField).withNullRepresentation("").withValidator(str -> str.length() >= 3, "must contain at least 3 characters").bind(Item::getName, Item::setName);
        binder.forField(descriptionField).withNullRepresentation("").withValidator(str -> str.length() >= 3, "must contain at least 3 characters").bind(Item::getDescription, Item::setDescription);
        binder.forField(priceField).withNullRepresentation("0").withConverter(new StringToIntegerConverter("Must enter a number")).bind(Item::getPrice, Item::setPrice);
        binder.forField(availableQuantityField).withNullRepresentation("0").withConverter(new StringToIntegerConverter("Must enter a number")).bind(Item::getAvailableQuantity, Item::setAvailableQuantity);

        ArrayList<Type> types = new ArrayList<>();
        types.addAll(Arrays.asList(Type.values()));
        typeComboBox.setItems(types);
        typeComboBox.setEmptySelectionAllowed(false);

        ArrayList<Genre> genres = new ArrayList<>();
        Collections.addAll(genres, Genre.values());
        genreComboBox.setItems(genres);
        genreComboBox.setEmptySelectionAllowed(false);

        ArrayList<Brand> brands = new ArrayList<>();
        Collections.addAll(brands, Brand.values());
        brandComboBox.setItems(brands);
        brandComboBox.setEmptySelectionAllowed(false);

        UploadFile uploadSmallImage = new UploadFile("Small image", "upload");
        UploadFile uploadLargeImage = new UploadFile("Large image", "upload");

        uploadSmallImage.setWidth("400");
        uploadLargeImage.setWidth("400");
        uploadSmallImage.addFinishedListener(finishedEvent -> uploadSmallImage.setButtonCaption(finishedEvent.getFilename()));
        uploadLargeImage.addFinishedListener(finishedEvent -> uploadLargeImage.setButtonCaption(finishedEvent.getFilename()));


        Button confirmAdd = createButton("CONFIRM ADD");
        confirmAdd.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        confirmAdd.setIcon(VaadinIcons.CHECK);
        confirmAdd.setWidth("400");
        confirmAdd.addClickListener(clickEvent -> {
            if (nameField.isEmpty() || descriptionField.isEmpty() || brandComboBox.isEmpty() || priceField.isEmpty()
                    || availableQuantityField.isEmpty() || genreComboBox.isEmpty() || typeComboBox.isEmpty()
                    || uploadSmallImage.getFileName().isEmpty() || uploadLargeImage.getFileName().isEmpty()) {

                Notification.show("You have to fill all details to add item!");
            } else if (!binder.isValid()) {
                Notification.show("Please check the red fields!");
            } else if (!Objects.isNull(itemService.findItemByName(nameField.getValue()))) {
                Notification.show("This item name has been used! Please choose other");
            } else {
                Item item = new Item(nameField.getValue(), descriptionField.getValue(), brandComboBox.getValue(),
                        Integer.parseInt(priceField.getValue()), Integer.parseInt(availableQuantityField.getValue()),
                        genreComboBox.getValue(), typeComboBox.getValue(), "/img/" + uploadSmallImage.getFileName(),
                        "/img/" + uploadLargeImage.getFileName());

                itemService.addItem(item);
            }
        });


        content.addComponents(title, nameField, descriptionField, priceField, availableQuantityField, typeComboBox, genreComboBox, brandComboBox, uploadSmallImage, uploadLargeImage, confirmAdd);
        window.setContent(content);
        window.center();
        return window;
    }

    private Window verificationWindow(String text, Button.ClickListener listener) {
        Window window = new Window();
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Label label = new Label(text, ContentMode.PREFORMATTED);
        label.setStyleName(ValoTheme.LABEL_H2);
        verticalLayout.addComponent(label);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Button okButton = new Button("YES");
        okButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        okButton.setIcon(VaadinIcons.CHECK);
        okButton.addClickListener(listener);
        okButton.addClickListener(clickEvent -> window.close());
        okButton.setWidth("150");
        okButton.setHeight("50");
        okButton.addStyleName(ValoTheme.BUTTON_HUGE);
        okButton.addStyleName(ValoTheme.BUTTON_DANGER);
        horizontalLayout.addComponent(okButton);
        Button cancelButton = new Button("NO");
        cancelButton.setIcon(VaadinIcons.CLOSE);
        cancelButton.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);

        cancelButton.addClickListener(clickEvent -> window.close());
        cancelButton.setWidth("150");
        cancelButton.setHeight("50");
        cancelButton.addStyleName(ValoTheme.BUTTON_HUGE);
        cancelButton.addStyleName(ValoTheme.BUTTON_DANGER);
        horizontalLayout.addComponents(okButton, cancelButton);
        verticalLayout.addComponent(horizontalLayout);

        window.setContent(verticalLayout);
        window.setWidth("350");
        window.setHeight("220");
        window.center();
        window.setModal(true);
        return window;
    }

    private TextField createTextField(String caption) {
        TextField textField = new TextField(caption);
        textField.setWidth("300");
        textField.addStyleNames(ValoTheme.TEXTFIELD_LARGE, "mystyle");
        return textField;
    }


}
