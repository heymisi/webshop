package kmihaly.mywebshop.view;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import kmihaly.mywebshop.UserValidator;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.service.DAOUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

import javax.annotation.PostConstruct;

//import kmihaly.mywebshop.UserValidator;

@SpringView(name = SignUpView.VIEW_NAME)
public class SignUpView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "signup";


    private UserValidator userValidate;

    private BindingResult error;

    @Autowired
    private DAOUserService userService;

    private  User user = new User();

    @PostConstruct
    void init() {
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Label label = new Label("SIGN IN");
        label.addStyleNames(ValoTheme.LABEL_H1);


        TextField username = new TextField("username");
        username.setIcon(VaadinIcons.USER);
        PasswordField passwordField = new PasswordField("password");
        passwordField.setIcon(VaadinIcons.PASSWORD);

        HorizontalLayout verticalLayout = new HorizontalLayout();

        Button submit = new Button("submit" , (Button.ClickListener) clickEvent -> {
            user = userService.findUserByName(username.toString());
            userService.signIn(username.getValue(),passwordField.getValue());
            });


        submit.setIcon(VaadinIcons.USER_CHECK);
        Button register = new Button("RegisterView", (Button.ClickListener) clickEvent -> getUI().getNavigator().navigateTo(RegisterView.VIEW_NAME));
        register.setIcon(VaadinIcons.SWORD);
        verticalLayout.addComponent(submit);
        verticalLayout.addComponent(register);
        Button forgotten = new Button("forgot password?", (Button.ClickListener) clickEvent -> getUI().getNavigator().navigateTo(PasswordResetView.VIEW_NAME));
        forgotten.setStyleName(ValoTheme.BUTTON_BORDERLESS);

        addComponents(label,username,passwordField,forgotten,verticalLayout);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {}
}

