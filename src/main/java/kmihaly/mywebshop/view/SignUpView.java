package kmihaly.mywebshop.view;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import kmihaly.mywebshop.service.DAOUserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Objects;


@SpringView(name = SignUpView.VIEW_NAME)
public class SignUpView extends HorizontalLayout implements View {
    public static final String VIEW_NAME = "signup";

    private Binder<User> binder = new Binder<>();
    @Autowired
    private DAOUserService userService;

    private User loggedUser = ((MyUI) UI.getCurrent()).getUser();

    @PostConstruct
    void init() {
        if (!loggedUser.getUserType().equals(UserType.GUEST)) {
            setSizeFull();

            VerticalLayout sideBar = new VerticalLayout();
            VerticalLayout content = new VerticalLayout();

            Panel contentPanel = new Panel();
            contentPanel.setSizeFull();

            sideBar.setSizeFull();
            sideBar.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
            Label nameLabel = new Label(loggedUser.getUserName().toUpperCase());
            nameLabel.addStyleNames(ValoTheme.LABEL_H1, "labelForName");
            sideBar.addComponent(nameLabel);
            sideBar.addComponent(createMenuButton("YOUR ACCOUNT", accountInformationLayout(), contentPanel));
            sideBar.addComponent(createMenuButton("LOG OUT", logOutLayout(), contentPanel));

            contentPanel.setContent(accountInformationLayout());
            content.addComponent(contentPanel);
            addComponent(sideBar);
            addComponent(content);
            setExpandRatio(sideBar, 2);
            setExpandRatio(content, 7);
            setSpacing(false);
            setMargin(false);

        } else {
            setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
            setSizeFull();
            VerticalLayout singInLayout = new VerticalLayout();
            singInLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
            Label title = new Label("SIGN IN");
            Label text = new Label("If you already have an account sign in,\n" + "or if you don't have click the register button!", ContentMode.PREFORMATTED);
            title.addStyleNames(ValoTheme.LABEL_H1, ValoTheme.LABEL_BOLD);
            text.setStyleName(ValoTheme.LABEL_H2);
            TextField username = new TextField("username");
            username.setIcon(VaadinIcons.USER);
            username.setWidth("300");
            username.addStyleNames(ValoTheme.TEXTFIELD_LARGE, "mystyle");
            binder.forField(username).withNullRepresentation("").withValidator(name -> name.length() >= 3, "must contain at least 3 characters").bind(User::getUserName, User::setUserName);
            PasswordField password = new PasswordField("password");
            password.setWidth("300");
            password.setIcon(VaadinIcons.PASSWORD);
            password.addStyleNames(ValoTheme.TEXTFIELD_LARGE, "mystyle");

            binder.forField(password).withNullRepresentation("").withValidator(psw -> psw.length() >= 3, "must contain at least 3 characters").bind(User::getPassword, User::setPassword);

            HorizontalLayout buttons = new HorizontalLayout();

            Button submit = submitButton(username, password);
            Button register = new Button("Register", (Button.ClickListener) clickEvent -> getUI().getNavigator().navigateTo(RegisterView.VIEW_NAME));
            register.setIcon(VaadinIcons.SWORD);
            register.setWidth("200");
            register.setStyleName(ValoTheme.BUTTON_DANGER);
            buttons.addComponent(submit);
            buttons.addComponent(register);
            Button forgotten = new Button("forgot password?", (Button.ClickListener) clickEvent -> getUI().getNavigator().navigateTo(PasswordResetView.VIEW_NAME));
            forgotten.setStyleName(ValoTheme.BUTTON_BORDERLESS);

            singInLayout.addComponents(title, text, username, password, forgotten, buttons);
            addComponent(singInLayout);
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    private Button submitButton(TextField username, TextField password) {


        Button submit = new Button("submit", (Button.ClickListener) clickEvent -> {
            Notification notification = new Notification("");
            notification.setStyleName(ValoTheme.NOTIFICATION_ERROR);
            if (username.isEmpty() && password.isEmpty()) {
                notification.show("you have to type your username and password!");
            } else if (username.isEmpty()) {
                notification.show("you have to type your username!");
            } else if (password.isEmpty()) {
                notification.show("you have to type your password!");
            } else {
                User user = userService.findUserByName(username.getValue());
                if (Objects.isNull(user)) {
                    notification.show("there is no such username!");
                } else if (!user.getPassword().equals(password.getValue())) {
                    notification.show("your password was incorrect!");
                } else {
                    ((MyUI) UI.getCurrent()).setUser(user);
                    userService.signIn(username.getValue(), password.getValue());
                    notification.show(" Welcome: " + user.getFirstName() + " " + user.getLastName());
                    UI.getCurrent().getPage().reload();
                    getUI().getNavigator().navigateTo(MainPageView.VIEW_NAME);

                }
            }
        });
        submit.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        submit.setStyleName(ValoTheme.BUTTON_DANGER);
        submit.setWidth("200");
        submit.setIcon(VaadinIcons.USER_CHECK);
        return submit;
    }

    private VerticalLayout logOutLayout() {
        VerticalLayout logOutLayout = new VerticalLayout();
        logOutLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        logOutLayout.setSizeFull();
        Label label = new Label("Are you sure about log out?\n" + "All item in your bag will be removed!", ContentMode.PREFORMATTED);
        label.setStyleName(ValoTheme.LABEL_H2);
        addComponents(label);
        Button logoutButton = new Button("LOG OUT", clickEvent -> {
            ((MyUI) UI.getCurrent()).setUser(new User("guest", "guest", "guest", "guest", "guest", "guest", "guest", UserType.GUEST));
            UI.getCurrent().getPage().reload();
            Notification.show("you have been logged out!");
            getUI().getNavigator().navigateTo(MainPageView.VIEW_NAME);

        });
        logoutButton.setIcon(VaadinIcons.EXIT);
        logoutButton.addStyleNames(ValoTheme.BUTTON_DANGER, "deletebutton");
        logoutButton.setWidth("300");
        logoutButton.setHeight("55");

        logOutLayout.addComponents(label, logoutButton);
        return logOutLayout;
    }


    private VerticalLayout accountInformationLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        verticalLayout.setSizeFull();
        Label title = new Label("ACCOUNT INFORMATION ");
        Label text = new Label("Feel free to edit any of your details below so your account is totally up to date.", ContentMode.PREFORMATTED);
        text.setStyleName(ValoTheme.LABEL_H2);
        title.addStyleNames(ValoTheme.LABEL_H1, ValoTheme.LABEL_BOLD);

        FormLayout formLayout = new FormLayout();
        formLayout.setSizeUndefined();
        formLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Binder<User> binderForAccountInfo = new Binder<>();

        TextField firstName = createTextField("FIRST NAME:");
        firstName.setPlaceholder(loggedUser.getFirstName());
        binderForAccountInfo.forField(firstName).withValidator(name -> name.length() >= 3 || name.length() == 0, "must contain at least 3 characters")
                .bind(User::getFirstName, User::setFirstName);


        TextField lastName = createTextField("LAST NAME:");
        lastName.setPlaceholder(loggedUser.getLastName());
        binderForAccountInfo.forField(lastName).withValidator(name -> name.length() >= 3 || name.length() == 0, "must contain at least 3 characters")
                .bind(User::getLastName, User::setLastName);


        TextField email = createTextField("EMAIL ADDRESS:");
        binderForAccountInfo.forField(email).withNullRepresentation("").withValidator(new EmailValidator("This doesn't look like a valid email address"))
                .bind(User::getEmail, User::setEmail);
        email.setPlaceholder(loggedUser.getEmail());

        TextField address = createTextField("ADDRESS:");
        address.setPlaceholder(loggedUser.getAddress());
        binderForAccountInfo.forField(address).withValidator(name -> name.length() >= 3 || name.length() == 0, "must contain at least 3 characters")
                .bind(User::getAddress, User::setAddress);

        formLayout.addComponents(firstName, lastName, email, address);
        verticalLayout.addComponents(title, text, formLayout);
        verticalLayout.addComponent(saveChangesButton(firstName, lastName, email, address, binderForAccountInfo));
        return verticalLayout;
    }

    private Button saveChangesButton(TextField firstName, TextField lastName, TextField email, TextField address, Binder<User> binder) {
        Button button = createButton("SAVE CHANGES");
        button.setIcon(VaadinIcons.REFRESH);
        button.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        button.setStyleName(ValoTheme.BUTTON_DANGER);
        button.addClickListener(clickEvent -> {

            if (!firstName.getValue().isEmpty()) {
                loggedUser.setFirstName(firstName.getValue());
            }
            if (!lastName.getValue().isEmpty()) {
                loggedUser.setLastName(lastName.getValue());
            }
            if (!email.getValue().isEmpty()) {
                loggedUser.setEmail(email.getValue());
            }
            if (!address.getValue().isEmpty()) {
                loggedUser.setAddress(address.getValue());
            }
            if (!binder.isValid()) {
                Notification.show("Please check red fields!");
            } else if (!firstName.getValue().isEmpty() || !lastName.getValue().isEmpty() || !email.getValue().isEmpty() || !address.getValue().isEmpty()) {
                userService.createUser(loggedUser);
                Notification.show("You successfully changed details!");
            } else {
                Notification.show("If you want to change your details your have to fill the below areas");
            }
        });
        return button;
    }

    private TextField createTextField(String caption) {
        TextField textField = new TextField(caption);
        textField.setWidth("350");
        textField.addStyleNames(ValoTheme.TEXTAREA_LARGE, ValoTheme.TEXTFIELD_INLINE_ICON, "mystyle");
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        return textField;
    }

    private Button createButton(String caption) {
        Button button = new Button(caption);
        button.addStyleNames(ValoTheme.BUTTON_DANGER);
        button.setWidth("240");
        button.setHeight("50");
        return button;
    }


    private Button createMenuButton(String caption, VerticalLayout layout, Panel panel) {
        Button button = new Button(caption, clickEvent -> panel.setContent(layout));
        button.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
        button.setWidth("380");
        button.setHeight("60");

        return button;
    }
}



