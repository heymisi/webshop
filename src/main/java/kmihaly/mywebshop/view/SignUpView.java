package kmihaly.mywebshop.view;

import com.sun.org.apache.bcel.internal.generic.LADD;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import kmihaly.mywebshop.service.DAOUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

import javax.annotation.PostConstruct;
import java.util.Objects;


@SpringView(name = SignUpView.VIEW_NAME)
public class SignUpView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "signup";

    private Binder<User> binder = new Binder<>();
    @Autowired
    private DAOUserService userService;

    private User loggedUser = ((MyUI) UI.getCurrent()).getUser();

    @PostConstruct
    void init() {
        if (!loggedUser.getUserType().equals(UserType.GUEST)) {
            setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
            Label label = new Label("Are you sure about log out?\n" + "All item in your bag will be removed!", ContentMode.PREFORMATTED);
            label.setStyleName(ValoTheme.LABEL_H2);
            addComponents(label);
            Button logoutButton = new Button("LOG OUT", clickEvent -> {
                ((MyUI) UI.getCurrent()).setUser(new User("guest", "guest", "guest", "guest", "guest", "guest","guest",   UserType.GUEST));
                UI.getCurrent().getPage().reload();
                Notification.show("you have been logged out!");
                getUI().getNavigator().navigateTo(MainPageView.VIEW_NAME);

            });
            logoutButton.setIcon(VaadinIcons.EXIT);
            logoutButton.setStyleName(ValoTheme.BUTTON_DANGER);
            logoutButton.setWidth("240");
            addComponents(logoutButton);
        } else {
            setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
            Label title = new Label("SIGN IN");
            Label text = new Label("If you already have an account sign in,\n" + "or if you don't have click the register button!", ContentMode.PREFORMATTED);
            title.addStyleNames(ValoTheme.LABEL_H1,ValoTheme.LABEL_BOLD);
            text.setStyleName(ValoTheme.LABEL_H2);
            TextField username = new TextField("username");
            username.setIcon(VaadinIcons.USER);
            username.setWidth("300");
            username.addStyleNames(ValoTheme.TEXTFIELD_LARGE,"mystyle");
            binder.forField(username).withNullRepresentation("").withValidator(name -> name.length() >= 3, "must contain at least 3 characters").bind(User::getUserName, User::setUserName);
            PasswordField password = new PasswordField("password");
            password.setWidth("300");
            password.setIcon(VaadinIcons.PASSWORD);
            password.addStyleNames(ValoTheme.TEXTFIELD_LARGE,"mystyle");

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

            addComponents(title, text, username, password, forgotten, buttons);
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


}



