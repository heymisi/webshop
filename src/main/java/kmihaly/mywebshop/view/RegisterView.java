package kmihaly.mywebshop.view;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.service.DAOUserService;
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

        TextField username = new TextField("username");
        binder.forField(username).withNullRepresentation("").withValidator(name -> name.length() >= 4, "must contain at least 4 characters").bind(User::getUserName,User::setUserName);
        username.setPlaceholder("must be at least 4 characters");

        TextField firstName = new TextField("first name");
        binder.forField(firstName).withValidator(name -> name.length() >= 3, "must contain at least 3 characters").bind(User::getFirstName,User::setFirstName);

        TextField lastName = new TextField("last name");

        TextField email = new TextField("email");
        binder.forField(email).withValidator(new EmailValidator("This doesn't look like a valid email address"))
        .bind(User::getEmail,User::setEmail);

        TextField address = new TextField("address");

        PasswordField passwordField = new PasswordField("password");

        PasswordField passwordField2 = new PasswordField("password (again)");

        addComponents(label,username,firstName,lastName,email,address,passwordField,passwordField2);

        Button submit = new Button("submit", (Button.ClickListener) clickEvent -> {
            service.register(username.getValue(), firstName.getValue(), lastName.getValue(),
                    email.getValue(), address.getValue(), passwordField2.getValue());
        });

        addComponent(submit);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

}
