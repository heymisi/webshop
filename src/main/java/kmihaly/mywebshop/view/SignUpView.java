package kmihaly.mywebshop.view;

import com.vaadin.data.Binder;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import kmihaly.mywebshop.domain.model.user.User;
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

    private User user = new User();

    @PostConstruct
    void init() {
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Label label = new Label("SIGN IN");
        label.addStyleNames(ValoTheme.LABEL_H1);


        TextField username = new TextField("username");
        username.setIcon(VaadinIcons.USER);
        binder.forField(username).withNullRepresentation("").withValidator(name -> name.length() >= 3, "must contain at least 3 characters").bind(User::getUserName, User::setUserName);
        PasswordField passwordField = new PasswordField("password");
        passwordField.setIcon(VaadinIcons.PASSWORD);
        binder.forField(passwordField).withNullRepresentation("").withValidator(password -> password.length() >= 3, "must contain at least 3 characters").bind(User::getPassword, User::setPassword);


        HorizontalLayout buttons = new HorizontalLayout();
        Notification notification = new Notification("");


        Button submit = new Button("submit", (Button.ClickListener) clickEvent -> {


            if (username.isEmpty() && passwordField.isEmpty()) {
                notification.show("you have to type your username and password");
            } else if (username.isEmpty()) {
                notification.show("you have to type your username ");
            } else if (passwordField.isEmpty()) {
                notification.show("you have to type your password");
            } else {
                User user = userService.findUserByName(username.getValue());
                if (Objects.isNull(user)) {
                    notification.show("there is no such username");
                } else if (!user.getPassword().equals(passwordField.getValue())) {
                    notification.show("your password were incorrect");
                } else {
                    userService.signIn(username.getValue(), passwordField.getValue());
                    notification.show("successful login! Welcome: " +user.getFirstName());
                    getUI().getNavigator().navigateTo(MainPageView.VIEW_NAME);
                }
            }
        });
        submit.setIcon(VaadinIcons.USER_CHECK);
        Button register = new Button("Register", (Button.ClickListener) clickEvent -> getUI().getNavigator().navigateTo(RegisterView.VIEW_NAME));
        register.setIcon(VaadinIcons.SWORD);
        buttons.addComponent(submit);
        buttons.addComponent(register);
        Button forgotten = new Button("forgot password?", (Button.ClickListener) clickEvent -> getUI().getNavigator().navigateTo(PasswordResetView.VIEW_NAME));
        forgotten.setStyleName(ValoTheme.BUTTON_BORDERLESS);

        addComponents(label, username, passwordField, forgotten, buttons);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

}



