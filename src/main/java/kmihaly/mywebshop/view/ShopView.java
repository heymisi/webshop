package kmihaly.mywebshop.view;

import com.vaadin.data.HasValue;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.ContentMode;
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

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.valueOf;

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


    @PostConstruct
    void init() {

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

        tabs.addTab(tab1, "MEN");
        tabs.addTab(tab2, "WOMEN");
        tabs.setSizeFull();

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
        setSizeFull();
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

        Slider slider = new Slider("Choose Price");
        slider.setMax(300);
        slider.setResolution(0);
        slider.setWidth("180");
        slider.addValueChangeListener((HasValue.ValueChangeEvent<Double> event) -> {
            Double val = event.getValue();
            price.setValue(Math.round(val) + "$");
        });

        Button searchButton = new Button("Search");

        Label title = new Label();
        title.setStyleName(ValoTheme.LABEL_H2);
        if (genreType.equals(Genre.MEN)) {
            title.setValue("MEN CLOTHES");
        } else {
            title.setValue("WOMEN CLOTHES");
        }
        Label rowCount = new Label(itemService.searchByGenre(genreType).size() + " clothes found");
        rowCount.addStyleNames(ValoTheme.LABEL_BOLD);
        searchButton.addClickListener((Button.ClickListener) clickEvent -> {
            List<Item> filteredItems = itemService.multipleSearch(search.getValue(), genreType.name(), brandFilter.getValue(), typeFilter.getValue());
            foundItem = filteredItems.size();
            rowCount.setValue(foundItem + " clothes found");
            items.setItems(filteredItems);
        });
        Button addItem = createButton("ADD ITEM");
        addItem.addClickListener(clickEvent -> {
            MyUI.getCurrent().addWindow(addItemWindow());
        });
        Button deleteItem = createButton("DELETE ITEM");
        deleteItem.addClickListener(clickEvent -> {
            Optional<Item> item = items.getSelectionModel().getFirstSelectedItem();
            itemService.deleteItem(item.get());
        });
        addItem.setVisible(false);
        deleteItem.setVisible(false);

        if (Objects.nonNull(loggedUser) && loggedUser.getUserType().equals(UserType.ADMIN)) {
            addItem.setVisible(true);
            deleteItem.setVisible(true);
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

    public void setUpItems(Grid<Item> items, Genre genreType) {
        items.addComponentColumn(item -> {
            Image image = new Image("Image from file", new FileResource(new File(basePath + item.getSmallImagePath())));
            return image;
        }).setCaption("picture").setWidth(220);

        items.setItems(itemService.searchByGenre(genreType));
        items.addColumn(Item::getName).setCaption("name");
        items.addColumn(Item::getPrice).setCaption("price");
        items.setBodyRowHeight(200);
        items.addComponentColumn(this::itemDetailsButton).setCaption("more info");
        items.setSelectionMode(Grid.SelectionMode.SINGLE);
        items.setSizeFull();
    }


    private Button createButton(String string) {
        Button button = new Button(string);
        button.setStyleName(ValoTheme.BUTTON_DANGER);
        button.setWidth("180");
        return button;
    }

    private Button itemDetailsButton(Item item) {
        Button button = new Button("Click for detailed info");
        button.setStyleName(ValoTheme.BUTTON_DANGER);
        button.addClickListener(event -> {
            MyUI.getCurrent().addWindow(itemDetails(item));
        });
        return button;
    }

    private Button changeItemButton(Item item) {
        Button button = new Button("Change item details");
        button.setStyleName(ValoTheme.BUTTON_DANGER);
        button.addClickListener(clickEvent -> {
            MyUI.getCurrent().addWindow(changeItemDetailsWindow(item));
        });
        button.setVisible(false);
        return button;
    }

    private Window addItemWindow(){
        Window window = new Window();
        VerticalLayout content = new VerticalLayout();

        content.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        TextField nameField = new TextField("name:");
        TextField descriptionField = new TextField("description:");
        TextField priceField = new TextField("price:");
        TextField availableQuantityField = new TextField("quantity:");
        TextField typeField = new TextField("type:");
        TextField genreField= new TextField("genre:");
        TextField brandField= new TextField("brand:");
        TextField imageSmallField = new TextField("small image path:");
        TextField imageBigField = new TextField("large image path:");

        Button confirmAdd = createButton("CONFIRM ADD");
        confirmAdd.addClickListener(clickEvent -> {
            Item item = new Item(nameField.getValue(), descriptionField.getValue(), Brand.valueOf(brandField.getValue()), Integer.parseInt(priceField.getValue()),
                    Integer.parseInt(availableQuantityField.getValue()), Genre.valueOf(genreField.getValue()),
                    Type.valueOf(typeField.getValue()), imageBigField.getValue(), imageBigField.getValue());
            itemService.addItem(item);
        });


        content.addComponents(nameField,descriptionField,priceField,availableQuantityField,typeField,genreField,brandField,imageBigField,imageSmallField,confirmAdd);
        content.setSizeFull();
        window.setContent(content);
        window.center();
        window.setSizeFull();
        return window;
    }


    private Window changeItemDetailsWindow(Item item) {
        Window window = new Window();
        VerticalLayout content = new VerticalLayout();


        content.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        TextField nameField = new TextField("name:");
        nameField.setPlaceholder(item.getName());

        TextField descriptionField = new TextField("description:");
        descriptionField.setPlaceholder(item.getDescription());

        TextField priceField = new TextField("price:");
        priceField.setPlaceholder(valueOf(item.getPrice()));

        TextField availableQuantityField = new TextField("quantity:");
        availableQuantityField.setPlaceholder(valueOf(item.getAvailableQuantity()));

        TextField imageSmallField = new TextField("small image path:");
        imageSmallField.setPlaceholder(item.getSmallImagePath());

        TextField imageBigField = new TextField("large image path:");
        imageBigField.setPlaceholder(item.getBigImagePath());

        Button saveButton = createButton("SAVE");
        saveButton.addClickListener(clickEvent -> {
            if (!nameField.getValue().isEmpty()) {
                item.setName(nameField.getValue());
            }
            if (!descriptionField.getValue().isEmpty()) {
                item.setDescription(descriptionField.getValue());
            }
            if (!priceField.getValue().isEmpty()) {
                item.setPrice(Integer.parseInt(priceField.getValue()));
            }
            if (!availableQuantityField.getValue().isEmpty()) {
                item.setAvailableQuantity(Integer.parseInt(availableQuantityField.getValue()));
            }
            if (!imageSmallField.getValue().isEmpty()) {
                item.setSmallImagePath(imageSmallField.getValue());
            }
            if (!imageBigField.getValue().isEmpty()) {
                item.setBigImagePath(imageBigField.getValue());
            }

            if (!nameField.getValue().isEmpty() || !descriptionField.getValue().isEmpty()
                    || !priceField.getValue().isEmpty() || !availableQuantityField.getValue().isEmpty()
                    || !imageSmallField.getValue().isEmpty() || !imageBigField.getValue().isEmpty()) {

                itemService.changeItem(item);
                window.close();
                Notification.show("Successful change");

            } else {
                Notification.show("If you want to change your details your have to fill the below areas");
            }
        });

        content.addComponents(nameField, descriptionField, priceField, availableQuantityField, imageSmallField, imageBigField, saveButton);
        window.setContent(content);
        window.center();
        return window;
    }


    private Window itemDetails(Item item) {

        Window window = new Window();
        HorizontalLayout content = new HorizontalLayout();
        content.addComponent(new Image("", new FileResource(new File(basePath + item.getBigImagePath()))));

        VerticalLayout infoContent = new VerticalLayout();
        infoContent.addComponent(new Label("NAME: \n" + item.getName(), ContentMode.PREFORMATTED));
        infoContent.addComponent(new Label("DESCRIPTION: \n" + item.getDescription(), ContentMode.PREFORMATTED));
        infoContent.addComponent(new Label("BRAND: \n" + item.getBrand().toString(), ContentMode.PREFORMATTED));
        infoContent.addComponent(new Label("RATE: \n" + item.getRate(), ContentMode.PREFORMATTED));

        Label availableQuantityLabel = new Label("AVAILABLE QUANTITY:\n" + item.getAvailableQuantity(),ContentMode.PREFORMATTED);
        availableQuantityLabel.setVisible(false);
        infoContent.addComponents(availableQuantityLabel);
        Label sizeLabel = new Label("SIZE: ", ContentMode.PREFORMATTED);
        sizeLabel.setVisible(false);
        infoContent.addComponent(sizeLabel);

        ComboBox sizeBox = new ComboBox();
        Collection<String> sizes = new ArrayList<>();
        for (Size size : Size.values()) {
            sizes.add(size.toString());
        }
        sizeBox.setItems(sizes);
        sizeBox.setEmptySelectionCaption("Please select");
        infoContent.addComponent(sizeBox);
        sizeBox.setEmptySelectionAllowed(false);
        sizeBox.setVisible(false);

        ComboBox quantityBox = new ComboBox();
        List<Integer> collect = IntStream.range(1, 6).boxed().collect(Collectors.toList());
        quantityBox.setItems(collect);
        quantityBox.setEmptySelectionCaption("Please select");
        quantityBox.setEmptySelectionAllowed(false);
        quantityBox.setVisible(false);

        Label quantityLabel = new Label("QUANTITY: ");
        quantityLabel.setVisible(false);
        infoContent.addComponent(quantityLabel);
        infoContent.addComponent(quantityBox);

        infoContent.addComponent(new Label("PRICE: \n" + item.getPrice() + "$", ContentMode.PREFORMATTED));

        Label availableLabel = new Label("", ContentMode.PREFORMATTED);
        if (item.getAvailableQuantity() == 0) availableLabel.setValue("NOT AVAILABLE");
        else availableLabel.setValue("AVAILABLE");

        infoContent.addComponent(availableLabel);

        Button addToBag = createButton("ADD TO BAG");
        Button changeItem = changeItemButton(item);
        addToBag.setVisible(false);

        if (loggedUser.getUserType().equals(UserType.USER)) {
            addToBag.setVisible(true);
            quantityBox.setVisible(true);
            sizeBox.setVisible(true);
            sizeLabel.setVisible(true);
            quantityLabel.setVisible(true);
        }

        if (loggedUser.getUserType().equals(UserType.ADMIN)) {
            changeItem.setVisible(true);
            availableQuantityLabel.setVisible(true);
        }
        addToBag.addClickListener(event -> {

            if (item.getAvailableQuantity() == 0) {
                Notification.show("Sorry! The item currently not available");
            } else if (quantityBox.isEmpty()) {
                Notification.show("Please select quantity");
            } else if (sizeBox.isEmpty()) {
                Notification.show("Please select size");
            } else if (quantityBox.isEmpty() || sizeBox.isEmpty()) {
                Notification.show("Please select size and quantity");
            } else if (Objects.isNull(loggedUser)) {
                Notification.show("You have to be logged in to add an item to your bag!");
            } else {
                purchaseService.addItemToStorage(item, Integer.parseInt(quantityBox.getValue().toString()), loggedUser);
                Notification.show("This item has been added to your bag");
                window.close();
            }
        });

        infoContent.addComponents(addToBag, changeItem);

        infoContent.setSizeFull();

        content.addComponent(infoContent);
        window.setContent(content);
        window.center();
        window.setResizable(false);
        return window;
    }

}
