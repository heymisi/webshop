package kmihaly.mywebshop.view;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;
import javafx.scene.input.KeyCode;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.service.DAOUserService;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.concurrent.Future;


@SpringView(name = RegisterView.VIEW_NAME)
public class RegisterView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "register";

    @Autowired
    private DAOUserService service;

    private Binder<User> binder = new Binder<>();

    @PostConstruct
    void init() {
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        Label label = new Label("Create Account");
        label.setStyleName(ValoTheme.LABEL_H1);

        TextField username = createTextField("username (at least 4 characters)");
        binder.forField(username).withNullRepresentation("").withValidator(name -> name.length() >= 4, "must contain at least 4 characters").bind(User::getUserName, User::setUserName);
        TextField firstName = createTextField("first name");
        binder.forField(firstName).withValidator(name -> name.length() >= 3, "must contain at least 3 characters").bind(User::getFirstName, User::setFirstName);
        TextField lastName = createTextField("last name");
        TextField email = createTextField("email");
        binder.forField(email).withValidator(new EmailValidator("This doesn't look like a valid email address"))
                .bind(User::getEmail, User::setEmail);
        TextField address = createTextField("address");
        PasswordField passwordField = new PasswordField("password (at least 6 characters)");
        binder.forField(passwordField).withValidator(name -> name.length() >= 6, "must contain at least 3 characters").bind(User::getPassword, User::setPassword);
        passwordField.setWidth("220");
        passwordField.setStyleName(ValoTheme.TEXTAREA_LARGE);
        PasswordField passwordField2 = new PasswordField("password (again)");
        binder.forField(passwordField2).withValidator(name -> name.length() >= 6, "must contain at least 3 characters").bind(User::getPassword, User::setPassword);
        passwordField2.setWidth("220");
        passwordField2.setStyleName(ValoTheme.TEXTAREA_LARGE);
        addComponents(label, username, firstName, lastName, email, address, passwordField, passwordField2);

        Button submit = new Button("submit", (Button.ClickListener) clickEvent -> {
            if (service.isUserNameUsed(username.getValue())) {
                Notification.show("Sorry, this username has already used!");
            } else if (!service.isPasswordsEquals(passwordField.toString(), passwordField2.toString())) {
                Notification.show("You have to type the same password!");
            } else if (!username.getValue().isEmpty() && !firstName.getValue().isEmpty() && !lastName.getValue().isEmpty() && !email.getValue().isEmpty() &&
                    !address.getValue().isEmpty() && !passwordField.getValue().isEmpty() && !passwordField2.getValue().isEmpty()) {

                service.register(username.getValue(), firstName.getValue(), lastName.getValue(),
                        email.getValue(), address.getValue(), passwordField2.getValue());
                Notification.show("Successful registration");
                getUI().getNavigator().navigateTo(MainPageView.VIEW_NAME);

            } else {
                Notification.show("You have to fill all the details to register!");
            }

        });
        submit.setWidth("220");
        submit.setStyleName(ValoTheme.BUTTON_DANGER);
        submit.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        addComponent(submit);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    private TextField createTextField(String caption) {
        TextField textField = new TextField(caption);
        textField.setWidth("220");
        textField.setStyleName(ValoTheme.TEXTAREA_LARGE);

        return textField;
    }

}
