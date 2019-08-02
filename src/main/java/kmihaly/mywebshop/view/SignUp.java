package kmihaly.mywebshop.view;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = SignUp.VIEW_NAME)
public class SignUp extends VerticalLayout implements View {
    public static final String VIEW_NAME = "signup";


    public SignUp() {
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        TextField username = new TextField("username");
        username.setIcon(VaadinIcons.USER);
        PasswordField passwordField = new PasswordField("password");
        passwordField.setIcon(VaadinIcons.PASSWORD);

        addComponent(username);
        addComponent(passwordField);

        HorizontalLayout verticalLayout = new HorizontalLayout();
        Button submit = new Button("submit");
        submit.setIcon(VaadinIcons.USER_CHECK);
        Button register = new Button("Register", (Button.ClickListener) clickEvent -> getUI().getNavigator().navigateTo(Register.VIEW_NAME));
        register.setIcon(VaadinIcons.SWORD);
        verticalLayout.addComponent(submit);
        verticalLayout.addComponent(register);
        Button forgotten = new Button("forgot password?", (Button.ClickListener) clickEvent -> getUI().getNavigator().navigateTo(PasswordReset.VIEW_NAME));
        forgotten.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        addComponent(forgotten);
        addComponent(verticalLayout);

    }
}

