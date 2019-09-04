package kmihaly.mywebshop.view;

import com.vaadin.annotations.PreserveOnRefresh;
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

    private User user = new User("guest", "guest", "guest", "guest", "guest", "guest", "guest", UserType.GUEST);

    private Panel springViewDisplay;


    @Autowired
    DAOUserService userService;

    @Autowired
    private EmailService emailService;

    private Button signUp = new Button("default");

    private Button bag = new Button("default");

    @Override
    protected void init(VaadinRequest request) {
        setUser(userService.findUserByName("usern"));
        springViewDisplay = new Panel();
        springViewDisplay.setSizeFull();
        VerticalLayout mainLayout = new VerticalLayout(navigationBarLayout(), springViewDisplay, footerLayout());
        setContent(mainLayout);

    }

    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_PRIMARY);
        button.setWidth("455");
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
            signUp.setCaption("Sign up");
            signUp.setIcon(VaadinIcons.SIGN_IN);
        } else if (user.getUserType().equals(UserType.ADMIN)) {
            signUp.setCaption(" Account information");
            signUp.setIcon(VaadinIcons.USER);
        } else {
            signUp.setCaption(" Account information");
            signUp.setIcon(VaadinIcons.USER);
        }
    }

    private HorizontalLayout navigationBarLayout() {

        HorizontalLayout content = new HorizontalLayout();
        content.setSizeFull();


        HorizontalLayout navigationBarLayout = new HorizontalLayout();

        navigationBarLayout.setSizeFull();

        bag = createNavigationButton("Bag", BagView.VIEW_NAME);
        bag.setIcon(VaadinIcons.BRIEFCASE);

        signUp = createNavigationButton("Sign Up", SignUpView.VIEW_NAME);
        signUp.setIcon(VaadinIcons.SIGN_IN);

        Button shop = createNavigationButton("Shop", ShopView.VIEW_NAME);
        shop.setIcon(VaadinIcons.CART);

        Button mainPage = createNavigationButton("Main Page", MainPageView.VIEW_NAME);
        mainPage.setIcon(VaadinIcons.SHOP);

        navigationBarLayout.addComponents(mainPage, shop, bag, signUp);
        HorizontalLayout loginInformLayout = new HorizontalLayout();

        content.addComponents(navigationBarLayout, loginInformLayout);
        content.setComponentAlignment(navigationBarLayout, Alignment.MIDDLE_LEFT);
        content.setExpandRatio(navigationBarLayout, 5);

        return content;
    }

    private HorizontalLayout footerLayout() {

        HorizontalLayout footer = new HorizontalLayout();
        VerticalLayout feedbackLayout = new VerticalLayout();
        feedbackLayout.setSizeUndefined();
        Label feedbackLabel = new Label("Please send us your feedback \n" + "about our website!", ContentMode.PREFORMATTED);
        feedbackLabel.setStyleName(ValoTheme.LABEL_H3);


        TextArea feedbackText = new TextArea();
        feedbackText.setPlaceholder("text goes here");
        feedbackText.setWidth("400");

        Button feedbackButton = new Button("SEND");
        feedbackButton.setIcon(VaadinIcons.ENVELOPE_O);
        feedbackButton.setWidth("400");
        feedbackButton.setStyleName(ValoTheme.BUTTON_DANGER);
        feedbackButton.addClickListener(clickEvent -> {
            if (feedbackText.isEmpty()) {
                Notification.show("Write something before send!");
            } else {
                try {
                    emailService.sendMail("heymisi99@gmail.com", "Feedback", feedbackText.getValue());
                    Notification.show("Thanks for your feedback!");
                } catch (javax.mail.MessagingException e) {
                    e.printStackTrace();
                }
            }
        });
        feedbackLayout.addComponents(feedbackLabel, feedbackText, feedbackButton);

        VerticalLayout buttonLayout = new VerticalLayout();
        buttonLayout.setSizeUndefined();
        Button aboutUsButton = createNavigationButton("About Us", AboutUsView.VIEW_NAME);
        aboutUsButton.setIcon(VaadinIcons.INFO_CIRCLE);
        aboutUsButton.addClickListener(clickEvent -> MyUI.getCurrent().setScrollTop(0));
        aboutUsButton.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        aboutUsButton.setWidth("500");
        buttonLayout.addComponent(aboutUsButton);

        footer.setSizeFull();
        footer.addComponent(buttonLayout);
        footer.addComponent(feedbackLayout);
        footer.setComponentAlignment(buttonLayout, Alignment.MIDDLE_LEFT);
        footer.setComponentAlignment(feedbackLayout, Alignment.MIDDLE_RIGHT);

        return footer;
    }

}
