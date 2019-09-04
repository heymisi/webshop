package kmihaly.mywebshop.view;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.service.DAOItemService;
import kmihaly.mywebshop.service.DAOPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;

import static com.vaadin.ui.UI.getCurrent;

@DesignRoot
@SpringView(name = MainPageView.VIEW_NAME)
public class MainPageView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "";

    @Autowired
    DAOItemService itemService;
    @Autowired
    DAOPurchaseService purchaseService;

    private String basePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    private User loggedUser = ((MyUI) UI.getCurrent()).getUser();

    private int size = 5;

    @PostConstruct
    void init() {
        Label newItemsLabel = new Label(VaadinIcons.ROAD.getHtml() + "Explore clothes");
        newItemsLabel.addStyleNames(ValoTheme.LABEL_H1,ValoTheme.LABEL_BOLD);
        newItemsLabel.setContentMode(ContentMode.HTML);
        Label cheapestItemsLabel = new Label(VaadinIcons.WALLET.getHtml() + " Best prices clothes");
        cheapestItemsLabel.addStyleNames(ValoTheme.LABEL_H1,ValoTheme.LABEL_BOLD);
        cheapestItemsLabel.setContentMode(ContentMode.HTML);
        Label bestRating = new Label(VaadinIcons.TRENDING_UP.getHtml() + " Best Rated clothes");
        bestRating.addStyleNames(ValoTheme.LABEL_H1,ValoTheme.LABEL_BOLD);
        bestRating.setContentMode(ContentMode.HTML);

        if(itemService.listItems().size()< size){
            size = itemService.listItems().size();
        }

        addComponents(cheapestItemsLabel, clothsLayout(itemService.findItemsOrderByPrice(size)),
                bestRating, clothsLayout(itemService.findItemsOrderByRate(size)), newItemsLabel, clothsLayout(itemService.getRandomItems(size)));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    private Panel clothsLayout(List<Item> items) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthUndefined();
        horizontalLayout.setHeightUndefined();
        Panel panel = new Panel();
        for (Item item : items) {
            Image image = new Image("", new FileResource(new File(basePath + item.getLargeImagePath())));
            image.setWidth("500");
            ItemDetails components = new ItemDetails(item, loggedUser, purchaseService, itemService);
            image.addClickListener(e -> getCurrent().addWindow(components));
            image.addStyleName("my-img-button");
            horizontalLayout.addComponent(image);
        }
        horizontalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        panel.setContent(horizontalLayout);
        panel.setSizeFull();
        return panel;
    }

}

