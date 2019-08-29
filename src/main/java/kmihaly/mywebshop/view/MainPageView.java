package kmihaly.mywebshop.view;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ImageRenderer;
import com.vaadin.ui.themes.ValoTheme;
import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.service.DAOItemService;
import kmihaly.mywebshop.service.DAOPurchaseService;
import org.aspectj.weaver.ast.Not;
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

    @PostConstruct
    void init() {
        Label newItemsLabel = new Label("Latest fashion");
        newItemsLabel.setStyleName(ValoTheme.LABEL_H1);
        Label cheapestItemsLabel = new Label("Best prices clothes");
        cheapestItemsLabel.setStyleName(ValoTheme.LABEL_H1);
        Label bestRating = new Label("Best Rated clothes");
        bestRating.setStyleName(ValoTheme.LABEL_H1);

        addComponents(newItemsLabel, clothsLayout(itemService.getRandomItems(4)), cheapestItemsLabel, clothsLayout(itemService.findItemsOrderByPrice()), bestRating, clothsLayout(itemService.getRandomItems(4)));
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
            ItemDetails components = new ItemDetails(item, loggedUser,purchaseService,itemService);
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

