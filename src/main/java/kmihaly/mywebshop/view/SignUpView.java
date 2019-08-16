package kmihaly.mywebshop.view;

import com.sun.org.apache.bcel.internal.generic.LADD;
import com.vaadin.data.Binder;
import com.vaadin.flow.component.dialog.Dialog;
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
            addComponents(new Label("You are logged in!\n" + "You can logout here:", ContentMode.PREFORMATTED));
            addComponents(new Button("LOG OUT", clickEvent -> {
                ((MyUI) UI.getCurrent()).setUser(new User("quest","quest","quest","quest","quest","quest", UserType.GUEST));
                Notification.show("you have been logged out!");
                getUI().getNavigator().navigateTo(MainPageView.VIEW_NAME);

            }));
        } else {
            setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
            Label title = new Label("SIGN IN");
            Label text = new Label( "If you already have an account sign in,\n" + "or if you don't have click the register button!",ContentMode.PREFORMATTED);
            title.addStyleNames(ValoTheme.LABEL_H1);

            TextField username = new TextField("username");
            username.setIcon(VaadinIcons.USER);
            binder.forField(username).withNullRepresentation("").withValidator(name -> name.length() >= 3, "must contain at least 3 characters").bind(User::getUserName, User::setUserName);
            PasswordField password = new PasswordField("password");
            password.setIcon(VaadinIcons.PASSWORD);
            binder.forField(password).withNullRepresentation("").withValidator(psw -> psw.length() >= 3, "must contain at least 3 characters").bind(User::getPassword, User::setPassword);

            HorizontalLayout buttons = new HorizontalLayout();

            Button submit = submitButton(username, password);

            submit.setIcon(VaadinIcons.USER_CHECK);
            Button register = new Button("Register", (Button.ClickListener) clickEvent -> getUI().getNavigator().navigateTo(RegisterView.VIEW_NAME));
            register.setIcon(VaadinIcons.SWORD);
            buttons.addComponent(submit);
            buttons.addComponent(register);
                    Button forgotten = new Button("forgot password?", (Button.ClickListener) clickEvent -> getUI().getNavigator().navigateTo(PasswordResetView.VIEW_NAME));
            forgotten.setStyleName(ValoTheme.BUTTON_BORDERLESS);

            addComponents(title,text, username, password, forgotten, buttons);
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    private Button submitButton(TextField username, TextField password) {

        Button submit = new Button("submit", (Button.ClickListener) clickEvent -> {
            if (username.isEmpty() && password.isEmpty()) {
                Notification.show("you have to type your username and password");
            } else if (username.isEmpty()) {
                Notification.show("you have to type your username ");
            } else if (password.isEmpty()) {
                Notification.show("you have to type your password");
            } else {
                User user = userService.findUserByName(username.getValue());
                if (Objects.isNull(user)) {
                    Notification.show("there is no such username");
                } else if (!user.getPassword().equals(password.getValue())) {
                    Notification.show("your password were incorrect");
                } else {
                    ((MyUI) UI.getCurrent()).setUser(user);
                    userService.signIn(username.getValue(), password.getValue());
                    Notification.show("successful login! Welcome: " + user.getFirstName() + " " + user.getLastName());
                    getUI().getNavigator().navigateTo(MainPageView.VIEW_NAME);

                }
            }
        });
        return submit;
    }


}



