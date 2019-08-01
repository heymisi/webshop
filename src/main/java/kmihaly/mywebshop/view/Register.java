package kmihaly.mywebshop.view;

import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import kmihaly.mywebshop.service.DAOUserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.awt.event.ActionListener;


@SpringView(name = "register")
public class Register extends VerticalLayout implements View {
    @Autowired
    private DAOUserService service;

    @PostConstruct
    public void init() {
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        TextField username = new TextField("username");
        username.setValue("legalább 4 karakter");
        username.setMaxLength(20);
        Label counter = new Label();
        counter.setValue("eddig : " + username.getValue().length() + " karakter");
        username.addValueChangeListener(event -> {
            int len = event.getValue().length();
            counter.setValue("eddig : " + len + " karakter");
        });
        username.setValueChangeMode(ValueChangeMode.EAGER);

        TextField firstName = new TextField("first name");
        TextField lastName = new TextField("last name");
        TextField email = new TextField("email");
        TextField address = new TextField("address");

        PasswordField passwordField = new PasswordField("password");

        PasswordField passwordField2 = new PasswordField("password (again)");

        addComponent(username);
        addComponent(counter);
        addComponent(firstName);
        addComponent(lastName);
        addComponent(email);
        addComponent(address);
        addComponent(passwordField);
        addComponent(passwordField2);

        Button submit = new Button("submit", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                service.register(username.getValue(), firstName.getValue(), lastName.getValue(), email.getValue(), address.getValue(), passwordField2.getValue());
            }
        });
        addComponent(submit);
    }

}