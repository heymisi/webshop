package kmihaly.mywebshop.view;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.domain.model.item.Size;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import kmihaly.mywebshop.service.DAOPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.vaadin.server.VaadinService.getCurrent;

class ItemDetails extends Window {

    private String basePath = getCurrent().getBaseDirectory().getAbsolutePath();

    @Autowired
    private DAOPurchaseService purchaseService;

    public ItemDetails(Item item, User loggedUser) {

        HorizontalLayout content = new HorizontalLayout();
        Image image = new Image("", new FileResource(new File(basePath + item.getLargeImagePath())));
        image.setHeight("1000");
        image.setWidth("1000");
        content.addComponent(image);

        VerticalLayout infoContent = new VerticalLayout();
        infoContent.addComponent(new Label("NAME: \n" + item.getName(), ContentMode.PREFORMATTED));
        infoContent.addComponent(new Label("DESCRIPTION: \n" + item.getDescription(), ContentMode.PREFORMATTED));
        infoContent.addComponent(new Label("BRAND: \n" + item.getBrand().toString(), ContentMode.PREFORMATTED));
        infoContent.addComponent(new Label("RATE: \n" + item.getRate(), ContentMode.PREFORMATTED));

        Label availableQuantityLabel = new Label("AVAILABLE QUANTITY:\n" + item.getAvailableQuantity(), ContentMode.PREFORMATTED);
        availableQuantityLabel.setVisible(false);
        infoContent.addComponents(availableQuantityLabel);
        Label sizeLabel = new Label("SIZE: ", ContentMode.PREFORMATTED);
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

        ComboBox<Integer> quantityBox = new ComboBox<>();
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

        Button addToBag = new Button("ADD TO BAG");
        Button changeItem = new Button("changeItemDetails");
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
            } else if (loggedUser.getUserType().equals(UserType.GUEST)) {
                Notification.show("You have to be logged in to add an item to your bag!");
            } else {
                purchaseService.addItemToStorage(item, Integer.parseInt(quantityBox.getValue().toString()), loggedUser);
                Notification.show("This item has been added to your bag");
                close();
            }
        });

        infoContent.addComponents(addToBag, changeItem);
        infoContent.setSizeFull();

        content.addComponent(infoContent);
        setContent(content);
        center();
        setModal(true);
        setResizable(false);
    }
}
