package kmihaly.mywebshop.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;

import com.vaadin.ui.themes.ValoTheme;
import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.service.DAOItemService;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
public class MyUI extends UI {
    @Autowired
    DAOItemService daoItemService;

    Navigator navigator;

    private Grid<Item> grid = new Grid<>(Item.class);


    @Override
    protected void init(VaadinRequest request) {
        Button mainMenu = new Button("mainPage", e -> getNavigator().navigateTo(""));
        mainMenu.addStyleNames(ValoTheme.MENU_TITLE);
        mainMenu.setWidth("200");
        Button shop = new Button("shop", e -> getNavigator().navigateTo("shop"));
        shop.addStyleNames(ValoTheme.MENU_ITEM);
        shop.setWidth("200");

        Button signUp = new Button("signUp", e -> getNavigator().navigateTo("signup"));
        signUp.addStyleNames(ValoTheme.MENU_ITEM);
        signUp.setWidth("200");

        Button bag = new Button("bag", e -> getNavigator().navigateTo("bag"));
        bag.addStyleNames(ValoTheme.MENU_ITEM);
        bag.setWidth("200");


        HorizontalLayout main = new HorizontalLayout(mainMenu, shop, signUp, bag);
        main.setSpacing(false);
        main.setMargin(false);


        VerticalLayout
                viewContainer = new VerticalLayout();
        viewContainer.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        viewContainer.setSizeFull();
        VerticalLayout mainLayout = new VerticalLayout(main, viewContainer);
        mainLayout.setExpandRatio(main,1);
        mainLayout.setExpandRatio(viewContainer,20);
        setContent(mainLayout);

        navigator = new Navigator(this, viewContainer);

        navigator.addView("", mainPage.class);
        navigator.addView("signup", SignUp.class);
        navigator.addView("shop", Bag.class);
        navigator.addView("bag", Shop.class);
        navigator.addView("register", Register.class);


    }


}
