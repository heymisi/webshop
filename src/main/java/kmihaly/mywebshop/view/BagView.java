package kmihaly.mywebshop.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import kmihaly.mywebshop.domain.model.item.Purchase;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.service.DAOPurchaseService;
import kmihaly.mywebshop.service.DAOUserService;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = BagView.VIEW_NAME)
public class BagView extends HorizontalLayout implements View {

    public static final String VIEW_NAME = "bag";
    @Autowired
    private DAOPurchaseService purchaseService;
    @Autowired
    private DAOUserService userService;

    private Grid<Purchase> purchases = new Grid<>(Purchase.class);
    private Grid<User> users = new Grid<>(User.class);


    @PostConstruct
    void init(){

        users.setItems(userService.listUsers());
        users.setSizeFull();

        VerticalLayout sideBar = new VerticalLayout();
        VerticalLayout mainLayout = new VerticalLayout();

        Button selectedItemsButton = createButton("Selected Items");
        Button accountInformationButton = createButton("Account information");
        Button myOrdersButton = createButton("My orders");

//        sideBar.addStyleNames(ValoTheme.MENU_ROOT);
        sideBar.setSizeFull();
        sideBar.addComponents(selectedItemsButton,accountInformationButton,myOrdersButton);

        Panel panel = new Panel();
        panel.setSizeUndefined();
        panel.setSizeFull();

        selectedItemsButton.addClickListener((Button.ClickListener) clickEvent -> {
            panel.setContent(selectedItemLayout());
        });

        accountInformationButton.addClickListener((Button.ClickListener) clickEvent -> {
            panel.setContent(accountInformation());
        });
        mainLayout.addComponent(panel);
        setSizeFull();
        addComponent(sideBar);
        addComponent(mainLayout);
        setExpandRatio(sideBar,1);
        setExpandRatio(mainLayout,4);
        setSpacing(false);
        setMargin(false);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event){}


    private Button createButton(String caption){
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_DANGER);
        button.setWidth("180");
        return button;
    }

    private VerticalLayout selectedItemLayout(){
        VerticalLayout verticalLayout = new VerticalLayout();
        purchases.setItems(purchaseService.listPurchases());
        purchases.setSizeFull();

        verticalLayout.addComponent(new Label("Selected Items"));
        verticalLayout.addComponent(purchases);
        verticalLayout.setSizeFull();
        verticalLayout.setSpacing(false);
        verticalLayout.setMargin(false);
        verticalLayout.setSizeFull();

        return verticalLayout;
    }

    private VerticalLayout accountInformation(){
        VerticalLayout verticalLayout = new VerticalLayout();

        verticalLayout.addComponent(new Label("Account Informations"));
        verticalLayout.setSizeFull();
        verticalLayout.setSpacing(false);
        verticalLayout.setMargin(false);
        verticalLayout.setSizeFull();
        return verticalLayout;
    }
}


