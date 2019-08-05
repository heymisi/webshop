package kmihaly.mywebshop.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import kmihaly.mywebshop.domain.model.item.Purchase;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.service.DAOPurchaseService;
import kmihaly.mywebshop.service.DAOUserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = BagView.VIEW_NAME)
public class BagView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "bag";
    @Autowired
    private DAOPurchaseService purchaseService;
    @Autowired
    private DAOUserService userService;

    private Grid<Purchase> purchases = new Grid<>(Purchase.class);
    private Grid<User> users = new Grid<>(User.class);


    @PostConstruct
    void init(){
        purchases.setItems(purchaseService.listPurchases());
        purchases.setSizeFull();
        users.setItems(userService.listUsers());
        users.setSizeFull();

        setSizeFull();
        addComponent(purchases);
        addComponent(users);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event){}
}


