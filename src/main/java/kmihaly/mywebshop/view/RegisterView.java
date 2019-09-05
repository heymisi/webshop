package kmihaly.mywebshop.view;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.service.DAOUserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;


@SpringView(name = RegisterView.VIEW_NAME)
public class RegisterView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "register";

    @Autowired
    private DAOUserService service;

    private Binder<User> binder = new Binder<>();

    @PostConstruct
    void init() {
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        FormLayout formLayout = new FormLayout();
        formLayout.setSizeUndefined();
        Label label = new Label("REGISTRATION");
        label.addStyleNames(ValoTheme.LABEL_H1, ValoTheme.LABEL_BOLD);
        TextField username = createTextField("username (at least 4 characters)");
        binder.forField(username).withNullRepresentation("").withValidator(name -> name.length() >= 4, "must contain at least 4 characters").bind(User::getUserName, User::setUserName);

        TextField firstName = createTextField("first name");
        binder.forField(firstName).withValidator(name -> name.length() >= 3, "must contain at least 3 characters").bind(User::getFirstName, User::setFirstName);

        TextField lastName = createTextField("last name");
        binder.forField(lastName).withValidator(name -> name.length() >= 3, "must contain at least 3 characters").bind(User::getFirstName, User::setFirstName);

        TextField email = createTextField("email");
        binder.forField(email).withValidator(new EmailValidator("This doesn't look like a valid email address"))
                .bind(User::getEmail, User::setEmail);
        email.setPlaceholder("example@gmail.com");

        TextField address = createTextField("address");
        binder.forField(address).withValidator(name -> name.length() >= 3, "must contain at least 3 characters").bind(User::getFirstName, User::setFirstName);
        address.setPlaceholder("City Street Identifier ");

        DateField birthDate = new DateField("birth date:");
        birthDate.setPlaceholder("YYYY.MM.DD");
        birthDate.setWidth("280");
        birthDate.addStyleNames(ValoTheme.TEXTAREA_LARGE, "mystyle");
        birthDate.setRequiredIndicatorVisible(true);

        PasswordField passwordField = new PasswordField("password (at least 6 characters)");
        binder.forField(passwordField).withValidator(name -> name.length() >= 6, "must contain at least 3 characters").bind(User::getPassword, User::setPassword);
        passwordField.setWidth("280");
        passwordField.addStyleNames(ValoTheme.TEXTAREA_LARGE, "mystyle");

        PasswordField passwordField2 = new PasswordField("password (again)");
        passwordField.setRequiredIndicatorVisible(true);
        binder.forField(passwordField2).withValidator(name -> name.length() >= 6, "must contain at least 3 characters").bind(User::getPassword, User::setPassword);
        passwordField2.setWidth("280");
        passwordField2.addStyleNames(ValoTheme.TEXTAREA_LARGE, "mystyle");
        passwordField2.setRequiredIndicatorVisible(true);

        CheckBox sendUpdate = new CheckBox("E-mail me updates");
        sendUpdate.setStyleName(ValoTheme.CHECKBOX_LARGE);
        HorizontalLayout actions = new HorizontalLayout();
        actions.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Button submit = new Button("SUBMIT", (Button.ClickListener) clickEvent -> {
            if (service.isUserNameUsed(username.getValue())) {
                Notification.show("Sorry, this username has already used!");
            } else if (!service.isPasswordsEquals(passwordField.toString(), passwordField2.toString())) {
                Notification.show("You have to type the same password!");
            } else if (!binder.isValid()) {
                Notification.show("There are problems in red fields");
            } else if (!username.getValue().isEmpty() && !firstName.getValue().isEmpty() && !lastName.getValue().isEmpty() && !email.getValue().isEmpty() &&
                    !address.getValue().isEmpty() && !birthDate.isEmpty() && !passwordField.getValue().isEmpty() && !passwordField2.getValue().isEmpty()) {

                service.register(username.getValue(), firstName.getValue(), lastName.getValue(),
                        email.getValue(), address.getValue(), birthDate.getValue().toString(), passwordField2.getValue());
                Notification.show("Successful registration!\n" + "Welcome " + firstName.getValue() + " " + lastName.getValue());
                ((MyUI) UI.getCurrent()).setUser(service.findUserByName(username.getValue()));
                UI.getCurrent().getPage().reload();
                getUI().getNavigator().navigateTo(MainPageView.VIEW_NAME);

            } else {
                Notification.show("You have to fill all the details to register!");
            }

        });
        submit.setStyleName(ValoTheme.BUTTON_DANGER);
        submit.setIcon(VaadinIcons.USER_CHECK);
        Button reset = new Button("RESET");
        reset.setWidth("150");
        reset.setStyleName(ValoTheme.BUTTON_DANGER);
        reset.setIcon(VaadinIcons.REFRESH);
        reset.addClickListener(clickEvent -> binder.readBean(null));
        actions.addComponents(submit, reset);
        submit.setWidth("150");
        submit.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        formLayout.addComponents(label, username, firstName, lastName, email, address, birthDate, passwordField, passwordField2, sendUpdate, actions);
        addComponent(formLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    private TextField createTextField(String caption) {
        TextField textField = new TextField(caption);
        textField.setWidth("280");
        textField.addStyleNames(ValoTheme.TEXTAREA_LARGE, "mystyle");
        textField.setRequiredIndicatorVisible(true);
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        return textField;
    }

}
