package kmihaly.mywebshop.view;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
@SpringView(name = "signup")
    public class SignUp extends VerticalLayout implements View {


        public SignUp() {
            setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

            TextField emailField = new TextField("username");

            PasswordField passwordField = new PasswordField("password");


            addComponent(emailField);
            addComponent(passwordField);

            HorizontalLayout verticalLayout = new HorizontalLayout();
            Button submit = new Button("submit");
            Button register = new Button("Register", new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent clickEvent) {
                    getUI().getNavigator().navigateTo("register");
                }
            });
            register.addStyleNames(ValoTheme.BUTTON_DANGER);

            verticalLayout.addComponent(submit);
            verticalLayout.addComponent(register);
            addComponent(verticalLayout);

        }
    }

