package kmihaly.mywebshop.view;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.PushStateNavigation;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.service.DAOUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
//import org.springframework.security.core.context.SecurityContextHolder;

@SpringUI
@Theme("mytheme")
@SpringViewDisplay
public class MyUI extends UI implements ViewDisplay {


    private User user;


    private Panel springViewDisplay;

     @Autowired
     private DAOUserService userService;

    @Override
    protected void init(VaadinRequest request) {


        user = userService.findUserByName("usern");
        HorizontalLayout navigationBar = new HorizontalLayout();
        Button bag = createNavigationButton("Bag", BagView.VIEW_NAME);
        Button signUp = createNavigationButton("Sign Up", SignUpView.VIEW_NAME);
        Button shop = createNavigationButton("Shop", ShopView.VIEW_NAME);
        navigationBar.addComponent(createNavigationButton("Main Page", MainPageView.VIEW_NAME));
        navigationBar.addComponent(shop);
        navigationBar.addComponent(signUp);
        navigationBar.addComponent(bag);

        bag.setIcon(VaadinIcons.BRIEFCASE);
        signUp.setIcon(VaadinIcons.USERS);
        shop.setIcon(VaadinIcons.CART);

        navigationBar.setMargin(false);
        navigationBar.setSpacing(false);

        springViewDisplay = new Panel();
        springViewDisplay.setSizeFull();

        HorizontalLayout footer = new HorizontalLayout();
        footer.addComponent(new Label("footer"));
        footer.setDefaultComponentAlignment(Alignment.BOTTOM_CENTER);
        VerticalLayout mainLayout = new VerticalLayout(navigationBar, springViewDisplay,footer);


        setContent(mainLayout);

    }

    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleNames(ValoTheme.BUTTON_PRIMARY);
        button.setWidth("200");
        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));

        return button;
    }

    @Override
    public void showView(View view) {
        springViewDisplay.setContent((Component) view);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



}
