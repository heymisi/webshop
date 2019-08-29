package kmihaly.mywebshop.view;

import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.converter.StringToIntegerConverter;
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
import kmihaly.mywebshop.domain.model.item.*;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import kmihaly.mywebshop.service.DAOItemService;
import kmihaly.mywebshop.service.DAOPurchaseService;
import kmihaly.mywebshop.service.DAOUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.teemu.ratingstars.RatingStars;

import javax.annotation.PostConstruct;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.valueOf;
import static kmihaly.mywebshop.view.MyUI.*;

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
        tabs.setStyleName(ValoTheme.TABSHEET_FRAMED);
        tabs.setStyleName(ValoTheme.TABSHEET_EQUAL_WIDTH_TABS);
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

        TextField search = new TextField("Search by name");
        search.setPlaceholder("All");
        ComboBox<String> typeFilter = new ComboBox<>("Choose Type");

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
        brandFilter.setPlaceholder("Choose Brand");
        brandFilter.setEmptySelectionCaption("All");
        brandFilter.setItems(brandTypes);

        Label price = new Label();

        Slider slider = new Slider("Choose Maximum Price",1,100);
        slider.setStyleName(ValoTheme.SLIDER_NO_INDICATOR);
        slider.setResolution(0);
        slider.setWidth("180");
        slider.addValueChangeListener((HasValue.ValueChangeEvent<Double> event) -> {
            Double val = event.getValue();
            price.setValue(Math.round(val) + "$");
        });

        Button searchButton = createButton("Search");
        searchButton.setIcon(VaadinIcons.SEARCH);

        Label title = new Label();
        title.setStyleName(ValoTheme.LABEL_H2);
        if (genreType.equals(Genre.MEN)) {
            title.setValue("MEN CLOTHES");
        } else {
            title.setValue("WOMEN CLOTHES");
        }
        Label rowCount = new Label(itemService.searchByGenre(genreType).size() + " clothes found");
        rowCount.addStyleName(ValoTheme.LABEL_BOLD);
        searchButton.addClickListener((Button.ClickListener) clickEvent -> {
            List<Item> filteredItems = itemService.multipleSearch(search.getValue(), genreType.name(), brandFilter.getValue(), typeFilter.getValue(),slider.getValue().intValue());
            foundItem = filteredItems.size();
            rowCount.setValue(foundItem + " clothes found");
            items.setItems(filteredItems);
        });
        Button addItem = createButton("ADD ITEM");
        addItem.addClickListener(clickEvent -> getCurrent().addWindow(addItemWindow()));
        Button deleteItem = createButton("DELETE ITEM");
        deleteItem.addClickListener(clickEvent -> {
            Set<Item> item = items.getSelectionModel().getSelectedItems();
            if (item.isEmpty()) {
                Notification.show("To delete an item you have to select one!");
            } else if (itemService.isSelected(item)) {
                Notification.show("idk if its good");
            } else {
                getCurrent().addWindow(verificationWindow("ARE YOU SURE TO DELETE?", clickEvent1 -> {
                    for (Item i : item) {
                        itemService.deleteItem(i);
                    }
                    items.setItems(itemService.searchByGenre(genreType));
                }));
            }
        });
        addItem.setVisible(false);
        deleteItem.setVisible(false);

        if (Objects.nonNull(loggedUser) && loggedUser.getUserType().equals(UserType.ADMIN)) {
            addItem.setVisible(true);
            deleteItem.setVisible(true);
            items.setSelectionMode(Grid.SelectionMode.MULTI);
        }

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
        items.addComponentColumn(item -> new Image("Image from file", new FileResource(new File(basePath + item.getSmallImagePath())))).setCaption("picture").setWidth(220);
        items.setItems(itemService.searchByGenre(genreType));
        items.addColumn(Item::getName).setCaption("name");
        items.addColumn(item -> item.getPrice() + "$").setCaption("price");
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
        button.addClickListener(event -> {
            getCurrent().addWindow(new ItemDetails(item,loggedUser,purchaseService,itemService));
        });
        return button;
    }


    private Window addItemWindow() {
        Window window = new Window();
        VerticalLayout content = new VerticalLayout();

        content.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        TextField nameField = createTextField("name:");
        TextField descriptionField = createTextField("description:");
        TextField priceField = createTextField("price:");
        TextField availableQuantityField = createTextField("quantity:");
        ComboBox<Type> typeComboBox = new ComboBox<>("type:");
        typeComboBox.setStyleName("mystyle");
        ComboBox<Genre> genreComboBox = new ComboBox<>("genre:");
        genreComboBox.setStyleName("mystyle");
        ComboBox<Brand> brandComboBox = new ComboBox<>("brand:");
        brandComboBox.setStyleName("mystyle");



        TextField imageSmallField = createTextField("small image path:");
        TextField imageBigField = createTextField("large image path:");


        binder.forField(nameField).withNullRepresentation("").withValidator(str -> str.length() >= 3, "must contain at least 3 characters").bind(Item::getName, Item::setName);
        binder.forField(descriptionField).withNullRepresentation("").withValidator(str -> str.length() >= 3, "must contain at least 3 characters").bind(Item::getDescription, Item::setDescription);
        binder.forField(priceField).withConverter(new StringToIntegerConverter("Must enter a number")).bind(Item::getPrice, Item::setPrice);
        binder.forField(availableQuantityField).withConverter(new StringToIntegerConverter("Must enter a number")).bind(Item::getAvailableQuantity, Item::setAvailableQuantity);
        binder.forField(imageSmallField).withNullRepresentation("").withValidator(str -> str.length() >= 3, "must contain at least 3 characters").bind(Item::getSmallImagePath, Item::setSmallImagePath);
        binder.forField(imageBigField).withNullRepresentation("").withValidator(str -> str.length() >= 3, "must contain at least 3 characters").bind(Item::getLargeImagePath, Item::setLargeImagePath);


        ArrayList<Type> types = new ArrayList<>();
        Arrays.asList(Type.values()).addAll(types);
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

        Button confirmAdd = createButton("CONFIRM ADD");
        confirmAdd.setIcon(VaadinIcons.CHECK);
        confirmAdd.addClickListener(clickEvent -> {
            if (!nameField.isEmpty() && !descriptionField.isEmpty() && !brandComboBox.isEmpty() && !priceField.isEmpty()
                    && !availableQuantityField.isEmpty() && !genreComboBox.isEmpty() && !typeComboBox.isEmpty() && !imageSmallField.isEmpty() && !imageBigField.isEmpty()) {
                Item item = new Item(nameField.getValue(), descriptionField.getValue(), brandComboBox.getValue(), Integer.parseInt(priceField.getValue()),
                        Integer.parseInt(availableQuantityField.getValue()), genreComboBox.getValue(),
                        typeComboBox.getValue(), imageBigField.getValue(), imageBigField.getValue());
                itemService.addItem(item);
            } else {
                Notification.show("You have to fill all details to add item!");
            }
        });


        content.addComponents(nameField, descriptionField, priceField, availableQuantityField, typeComboBox, genreComboBox, brandComboBox, imageBigField, imageSmallField, confirmAdd);
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
        okButton.addClickListener(listener);
        okButton.addClickListener(clickEvent -> window.close());
        okButton.setWidth("150");
        okButton.addStyleName(ValoTheme.BUTTON_HUGE);
        okButton.addStyleName(ValoTheme.BUTTON_DANGER);
        horizontalLayout.addComponent(okButton);
        Button cancelButton = new Button("NO");
        cancelButton.addClickListener(clickEvent -> window.close());
        cancelButton.setWidth("150");
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

    private TextField createTextField(String caption){
        TextField textField = new TextField(caption);
        textField.setWidth("200");
        textField.addStyleName("mystyle");
        return textField;
    }

}
