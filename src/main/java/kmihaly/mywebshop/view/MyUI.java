package kmihaly.mywebshop.view;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import kmihaly.mywebshop.service.DAOUserService;
import kmihaly.mywebshop.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@Theme("mytheme")
@SpringViewDisplay
@PreserveOnRefresh
@Widgetset("AppWidgetset")
public class MyUI extends UI implements ViewDisplay {

    private User user = new User("quest", "quest", "quest", "quest", "quest", "quest", UserType.GUEST);

    private Panel springViewDisplay;


    private Button loginInform = new Button("Welcome Guest!");
    @Autowired
    private DAOUserService userService;
    @Autowired
    private EmailService emailService;

    private Button signUp = new Button("default");

    private Button bag = new Button("default");

    @Override
    protected void init(VaadinRequest request) {

        springViewDisplay = new Panel();
        springViewDisplay.setSizeFull();
        VerticalLayout mainLayout = new VerticalLayout(navigationBarLayout(), springViewDisplay, footerLayout());
        setContent(mainLayout);

    }

    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_PRIMARY);
        button.setWidth("250");
        button.setHeight("50");
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

    @Override
    protected void refresh(VaadinRequest request) {
        if (user.getUserType().equals(UserType.GUEST)) {
            loginInform.setCaption("Welcome Guest!");
            signUp.setCaption("Sign up");
            signUp.setIcon(VaadinIcons.SIGN_IN);
            bag.setVisible(false);
        } else if (user.getUserType().equals(UserType.ADMIN)) {
            loginInform.setCaption("Welcome " + user.getUserName() + "! (ADMIN)");
            signUp.setCaption("Log out");
            signUp.setIcon(VaadinIcons.SIGN_OUT);
            bag.setVisible(true);
        } else {
            loginInform.setCaption("Welcome " + user.getUserName() + "!");
            signUp.setCaption("Log out");
            bag.setVisible(true);
        }
    }

    private GridLayout navigationBarLayout() {

        user = userService.findUserByName("usern");
        GridLayout navigationBar = new GridLayout(6, 2);
        navigationBar.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        bag = createNavigationButton("Bag", BagView.VIEW_NAME);
        signUp = createNavigationButton("Sign Up", SignUpView.VIEW_NAME);
        Button shop = createNavigationButton("Shop", ShopView.VIEW_NAME);
        Button mainPage = createNavigationButton("Main Page", MainPageView.VIEW_NAME);
        loginInform.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        loginInform.setEnabled(true);
        loginInform.setIcon(VaadinIcons.SMILEY_O);
        navigationBar.addComponent(mainPage, 0, 1);
        navigationBar.addComponent(shop, 1, 1);
        navigationBar.addComponent(signUp, 2, 1);
        navigationBar.addComponent(bag, 3, 1);
        navigationBar.addComponent(loginInform, 4, 0, 5, 0);
        navigationBar.setComponentAlignment(loginInform, Alignment.TOP_RIGHT);
        bag.setIcon(VaadinIcons.BRIEFCASE);
        bag.setVisible(false);
        signUp.setIcon(VaadinIcons.SIGN_IN);
        shop.setIcon(VaadinIcons.CART);

        navigationBar.setMargin(false);
        navigationBar.setSpacing(false);
        navigationBar.setStyleName(ValoTheme.MENU_APPEAR_ON_HOVER);
        return navigationBar;
    }

    private HorizontalLayout footerLayout() {

        HorizontalLayout footer = new HorizontalLayout();
        VerticalLayout feedbackLayout = new VerticalLayout();
        Label feedbackLabel = new Label("Please send us your feedback \n" + "about our website!", ContentMode.PREFORMATTED);
        feedbackLabel.setStyleName(ValoTheme.LABEL_H3);


        TextArea feedbackText = new TextArea();
        feedbackText.setPlaceholder("text goes here");
        feedbackText.setWidth("400");

        Button feedbackButton = new Button("SEND");
        feedbackButton.setWidth("400");
        feedbackButton.setStyleName(ValoTheme.BUTTON_DANGER);
        feedbackButton.addClickListener(clickEvent -> {
            try {
                emailService.sendMail("heymisi99@gmail.com", "Feedback", feedbackText.toString());
            } catch (javax.mail.MessagingException e) {
                e.printStackTrace();
            }
        });
        feedbackLayout.addComponents(feedbackLabel, feedbackText, feedbackButton);

        Button aboutUsButton = new Button("About Us");
        aboutUsButton.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        aboutUsButton.setWidth("500");
        Button helpButton = new Button("Help & Information");
        helpButton.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        helpButton.setWidth("500");

        footer.addComponents(aboutUsButton, helpButton, feedbackLayout);
        footer.setComponentAlignment(feedbackLayout, Alignment.TOP_RIGHT);
        footer.setComponentAlignment(aboutUsButton, Alignment.MIDDLE_CENTER);
        footer.setComponentAlignment(helpButton, Alignment.MIDDLE_CENTER);
        footer.setExpandRatio(feedbackLayout, 1);
        return footer;
    }

}
