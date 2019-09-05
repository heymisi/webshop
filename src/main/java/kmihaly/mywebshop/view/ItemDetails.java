package kmihaly.mywebshop.view;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.FileResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.domain.model.item.Size;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import kmihaly.mywebshop.service.DAOItemService;
import kmihaly.mywebshop.service.DAOPurchaseService;
import org.vaadin.teemu.ratingstars.RatingStars;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.vaadin.server.VaadinService.getCurrent;
import static java.lang.String.valueOf;

class ItemDetails extends Window implements View {

    private String basePath = getCurrent().getBaseDirectory().getAbsolutePath();
    private Binder<Item> binder = new Binder<>();

    ItemDetails(Item item, User loggedUser, DAOPurchaseService purchaseService, DAOItemService itemService) {

        HorizontalLayout content = new HorizontalLayout();
        Image image = new Image("", new FileResource(new File(basePath + item.getLargeImagePath())));
        image.setHeight("800");
        image.setWidth("800");
        content.addComponent(image);

        VerticalLayout infoContent = new VerticalLayout();
        infoContent.setSizeFull();
        infoContent.addComponent(createLabel("NAME:  " + item.getName()));
        infoContent.addComponent(createLabel("DESCRIPTION: \n" + item.getDescription()));
        infoContent.addComponent(createLabel("BRAND:  " + item.getBrand().toString()));

        Label availableQuantityLabel = createLabel("AVAILABLE QUANTITY:  ");
        Label availableQuantityDataLabel = createDataLabel(valueOf(item.getAvailableQuantity()));

        availableQuantityLabel.setVisible(loggedUser.getUserType().equals(UserType.ADMIN));
        availableQuantityDataLabel.setVisible(loggedUser.getUserType().equals(UserType.ADMIN));

        infoContent.addComponents(availableQuantityLabel, availableQuantityDataLabel);
        Label sizeLabel = createLabel("SIZE: ");
        sizeLabel.setVisible(loggedUser.getUserType().equals(UserType.USER));
        infoContent.addComponent(sizeLabel);

        ComboBox<String> sizeBox = new ComboBox<>();
        Collection<String> sizes = new ArrayList<>();
        for (Size size : Size.values()) {
            sizes.add(size.toString());
        }
        sizeBox.setItems(sizes);
        sizeBox.setEmptySelectionCaption("Please select");
        infoContent.addComponent(sizeBox);
        sizeBox.setEmptySelectionAllowed(false);
        sizeBox.setVisible(loggedUser.getUserType().equals(UserType.USER));
        sizeBox.setStyleName(ValoTheme.COMBOBOX_LARGE);

        ComboBox<Integer> quantityBox = new ComboBox<>();
        List<Integer> collect = IntStream.range(1, 6).boxed().collect(Collectors.toList());
        quantityBox.setItems(collect);
        quantityBox.setEmptySelectionCaption("Please select");
        quantityBox.setEmptySelectionAllowed(false);
        quantityBox.setVisible(loggedUser.getUserType().equals(UserType.USER));
        quantityBox.setStyleName(ValoTheme.COMBOBOX_LARGE);

        Label quantityLabel = createLabel("QUANTITY: ");
        quantityLabel.setVisible(loggedUser.getUserType().equals(UserType.USER));
        infoContent.addComponent(quantityLabel);
        infoContent.addComponent(quantityBox);

        infoContent.addComponent(createLabel("PRICE: " + item.getPrice() + "$"));

        RatingStars ratingStars = new RatingStars();
        ratingStars.setCaption("Rated by: " + item.getRate().getCounter());
        ratingStars.setStyleName("mystyleforRating");
        ratingStars.setValue(item.getRate().getValue());
        ratingStars.setEnabled(false);
        infoContent.addComponent(ratingStars);

        HorizontalLayout addToBagLayout = new HorizontalLayout();
        addToBagLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Button addToBagButton = new Button("ADD TO BAG");
        addToBagButton.setStyleName("addbutton");
        addToBagButton.setIcon(VaadinIcons.PLUS);
        addToBagButton.setHeight("50");
        Button changeItem = changeItemButton(item, itemService);
        changeItem.setStyleName(ValoTheme.BUTTON_DANGER);
        addToBagLayout.setVisible(loggedUser.getUserType().equals(UserType.USER));
        changeItem.setVisible(loggedUser.getUserType().equals(UserType.ADMIN));

        Image saveItem = new Image("", new FileResource(new File(basePath + "/img/hear.png")));
        saveItem.setHeight("40");
        saveItem.setWidth("40");
        saveItem.setStyleName("my-img-button");

        addToBagLayout.addComponents(addToBagButton, saveItem);

        saveItem.addClickListener(clickEvent -> {
            purchaseService.addItemToStorage(item, 1, loggedUser, false);
            Notification.show("This item has been added to your saved items");
            close();
        });

        addToBagButton.addClickListener(event -> {

            if (item.getAvailableQuantity() == 0) {
                Notification.show("Sorry! The item currently not available");
            } else if (quantityBox.isEmpty()) {
                Notification.show("Please select quantity");
            } else if (sizeBox.isEmpty()) {
                Notification.show("Please select size");
            } else if (quantityBox.isEmpty() || sizeBox.isEmpty()) {
                Notification.show("Please select size and quantity");
            } else {

                purchaseService.addItemToStorage(item, Integer.parseInt(quantityBox.getValue().toString()), loggedUser, true);
                Notification.show("This item has been added to your bag");
                close();
            }

        });


        infoContent.addComponents(addToBagLayout, changeItem);

        content.addComponent(infoContent);
        setContent(content);
        center();
        setModal(true);
        setResizable(false);
    }

    private Label createLabel(String caption) {
        Label label = new Label(caption, ContentMode.PREFORMATTED);
        label.setStyleName("mylabel");
        return label;
    }

    private Label createDataLabel(String caption) {
        Label label = new Label(caption, ContentMode.PREFORMATTED);
        label.setStyleName("mylabelfordata");
        return label;
    }

    private Button changeItemButton(Item item, DAOItemService itemService) {
        Button button = new Button("UPDATE");
        button.setWidth("400");
        button.setIcon(VaadinIcons.REFRESH);
        button.setStyleName(ValoTheme.BUTTON_DANGER);
        button.addClickListener(clickEvent -> UI.getCurrent().addWindow(changeItemDetailsWindow(item, itemService)));
        return button;
    }

    private Window changeItemDetailsWindow(Item item, DAOItemService itemService) {
        Window window = new Window();
        VerticalLayout content = new VerticalLayout();

        content.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        TextField nameField = createTextField("name:");
        nameField.setPlaceholder(item.getName());

        TextArea descriptionField = new TextArea("description:");
        descriptionField.setPlaceholder(item.getDescription());
        descriptionField.setWidth("500");
        descriptionField.addStyleNames(ValoTheme.TEXTAREA_LARGE, "mystyle");
        TextField priceField = createTextField("price:");
        priceField.setPlaceholder(valueOf(item.getPrice()));

        TextField availableQuantityField = createTextField("quantity:");
        availableQuantityField.setPlaceholder(valueOf(item.getAvailableQuantity()));

        binder.forField(nameField).withValidator(str -> str.length() >= 3 || str.length() == 0, "must contain at least 3 characters").bind(Item::getName, Item::setName);
        binder.forField(descriptionField).withValidator(str -> str.length() >= 3 || str.length() == 0, "must contain at least 3 characters").bind(Item::getDescription, Item::setDescription);
        binder.forField(priceField).withConverter(new StringToIntegerConverter("Must enter a number")).bind(Item::getPrice, Item::setPrice);
        binder.forField(availableQuantityField).withConverter(new StringToIntegerConverter("Must enter a number")).bind(Item::getAvailableQuantity, Item::setAvailableQuantity);


        UploadFile uploadSmallImage = new UploadFile("Small image", "upload");
        UploadFile uploadLargeImage = new UploadFile("Large image", "upload");
        uploadSmallImage.addFinishedListener(finishedEvent -> uploadSmallImage.setButtonCaption(finishedEvent.getFilename()));
        uploadLargeImage.addFinishedListener(finishedEvent -> uploadLargeImage.setButtonCaption(finishedEvent.getFilename()));

        Button saveButton = new Button("SAVE");
        saveButton.addStyleNames(ValoTheme.BUTTON_DANGER, "addbutton");
        saveButton.setWidth("500");
        saveButton.setIcon(VaadinIcons.CHECK_SQUARE);
        saveButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
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
            if (!uploadSmallImage.getFileName().isEmpty()) {
                item.setSmallImagePath("/img/" + uploadSmallImage.getFileName());
            }
            if (!uploadLargeImage.getFileName().isEmpty()) {
                item.setLargeImagePath("/img/" + uploadLargeImage.getFileName());
            }
            if (nameField.getValue().isEmpty() && descriptionField.getValue().isEmpty()
                    && priceField.getValue().isEmpty() && availableQuantityField.getValue().isEmpty()
                    && uploadLargeImage.getFileName().isEmpty() && uploadSmallImage.getFileName().isEmpty()) {

                Notification.show("If you want to change your details your have to fill the below areas");
            } else if (!binder.isValid()) {
                Notification.show("Please check the red fields!");
            } else if (!Objects.isNull(itemService.findItemByName(nameField.getValue()))) {
                Notification.show("This item name has been used! Please choose other");
            } else {
                itemService.changeItem(item);
                window.close();
                Notification.show("Item has been changed");
                this.close();
            }
        });

        content.addComponents(nameField, descriptionField, priceField, availableQuantityField, uploadSmallImage, uploadLargeImage, saveButton);
        window.setContent(content);
        window.center();
        window.setModal(true);
        return window;
    }


    private TextField createTextField(String caption) {
        TextField textField = new TextField(caption);
        textField.setWidth("500");
        textField.addStyleNames(ValoTheme.TEXTAREA_LARGE, "mystyle");

        return textField;
    }

}
