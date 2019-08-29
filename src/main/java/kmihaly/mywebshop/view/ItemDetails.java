package kmihaly.mywebshop.view;

import com.vaadin.navigator.View;
import com.vaadin.server.FileResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.domain.model.item.Size;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import kmihaly.mywebshop.service.DAOItemService;
import kmihaly.mywebshop.service.DAOPurchaseService;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.teemu.ratingstars.RatingStars;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.vaadin.server.VaadinService.getCurrent;
import static java.lang.String.valueOf;

class ItemDetails extends Window implements View {

    private String basePath = getCurrent().getBaseDirectory().getAbsolutePath();

    ItemDetails(Item item, User loggedUser, DAOPurchaseService purchaseService, DAOItemService itemService) {

        HorizontalLayout content = new HorizontalLayout();
        Image image = new Image("", new FileResource(new File(basePath + item.getLargeImagePath())));
        image.setHeight("1000");
        image.setWidth("1000");
        content.addComponent(image);

        VerticalLayout infoContent = new VerticalLayout();
        infoContent.addComponent(createLabel("NAME: \n\n" + item.getName()));
        infoContent.addComponent(createLabel("DESCRIPTION: \n\n" + item.getDescription()));
        infoContent.addComponent(createLabel("BRAND: \n\n" + item.getBrand().toString()));

        Label availableQuantityLabel = createLabel("AVAILABLE QUANTITY:\n\n" + item.getAvailableQuantity());
        availableQuantityLabel.setVisible(false);
        infoContent.addComponents(availableQuantityLabel);
        Label sizeLabel = createLabel("SIZE: ");
        sizeLabel.setVisible(false);
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
        sizeBox.setVisible(false);
        sizeBox.setStyleName(ValoTheme.COMBOBOX_LARGE);

        ComboBox<Integer> quantityBox = new ComboBox<>();
        List<Integer> collect = IntStream.range(1, 6).boxed().collect(Collectors.toList());
        quantityBox.setItems(collect);
        quantityBox.setEmptySelectionCaption("Please select");
        quantityBox.setEmptySelectionAllowed(false);
        quantityBox.setVisible(false);
        quantityBox.setStyleName(ValoTheme.COMBOBOX_LARGE);

        Label quantityLabel = createLabel("QUANTITY: ");
        quantityLabel.setVisible(false);
        infoContent.addComponent(quantityLabel);
        infoContent.addComponent(quantityBox);

        infoContent.addComponent(createLabel("PRICE: \n\n" + item.getPrice() + "$"));

        Label availableLabel = createLabel("");
        if (item.getAvailableQuantity() == 0) availableLabel.setValue("NOT AVAILABLE\n");
        else availableLabel.setValue("AVAILABLE\n");

        infoContent.addComponent(availableLabel);

        infoContent.addComponents(createLabel("RATING:\n"));

        RatingStars ratingStars = new RatingStars();
        ratingStars.setValue(item.getRate());
        ratingStars.setEnabled(false);
        infoContent.addComponent(ratingStars);


        HorizontalLayout addToBagLayout = new HorizontalLayout();
        addToBagLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Button addToBagButton = new Button("ADD TO BAG");
        addToBagButton.setStyleName(ValoTheme.BUTTON_DANGER);
        Button changeItem = changeItemButton(item,itemService);
        changeItem.setStyleName(ValoTheme.BUTTON_DANGER);
        addToBagLayout.setVisible(false);
        changeItem.setVisible(false);

        Image saveItem = new Image("", new FileResource(new File(basePath + "/img/hear.png")));
        saveItem.setHeight("40");
        saveItem.setWidth("40");
        saveItem.setStyleName("my-img-button");

        addToBagLayout.addComponents(addToBagButton,saveItem);

        if (loggedUser.getUserType().equals(UserType.USER)) {
            addToBagLayout.setVisible(true);
            quantityBox.setVisible(true);
            sizeBox.setVisible(true);
            sizeLabel.setVisible(true);
            quantityLabel.setVisible(true);
        }

        if (loggedUser.getUserType().equals(UserType.ADMIN)) {
            changeItem.setVisible(true);
            availableQuantityLabel.setVisible(true);
        }

        saveItem.addClickListener(clickEvent -> {
            purchaseService.addItemToStorage(item, 1, loggedUser,false);
            Notification.show("This item has been added to your saved items");
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

                purchaseService.addItemToStorage(item, Integer.parseInt(quantityBox.getValue().toString()), loggedUser,true);
                Notification.show("This item has been added to your bag");
                close();
            }
        });


        infoContent.addComponents(addToBagLayout, changeItem);
        infoContent.setSizeFull();

        content.addComponent(infoContent);
        setContent(content);
        center();
        setModal(true);
        setResizable(false);
    }

    private Label createLabel(String caption) {
        Label label = new Label(caption, ContentMode.PREFORMATTED);
        label.setStyleName(ValoTheme.LABEL_H3);
        return label;
    }

    private Button changeItemButton(Item item,DAOItemService itemService) {
        Button button = new Button("UPDATE");
        button.setStyleName(ValoTheme.BUTTON_DANGER);
        button.addClickListener(clickEvent -> UI.getCurrent().addWindow(changeItemDetailsWindow(item,itemService)));
        button.setVisible(false);
        return button;
    }

    private Window changeItemDetailsWindow(Item item,DAOItemService itemService) {
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
        imageBigField.setPlaceholder(item.getLargeImagePath());

        Button saveButton = new Button("SAVE");
        saveButton.setStyleName(ValoTheme.BUTTON_DANGER);
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
                item.setLargeImagePath(imageBigField.getValue());
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
        window.setModal(true);
        return window;
    }
}
