package kmihaly.mywebshop.view;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.service.DAOUserService;
import kmihaly.mywebshop.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;
import java.util.Objects;

@SpringView(name = PasswordResetView.VIEW_NAME)
public class PasswordResetView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "reset";

    private Binder<User> binder = new Binder<>();

    @Autowired
    EmailService emailService;

    @Autowired
    DAOUserService userService;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        Label header = new Label("Reset your password");
        header.addStyleNames(ValoTheme.LABEL_H1, ValoTheme.LABEL_BOLD);
        Label label = new Label("Type in your email address below and we'll send you\n" + "an email with your new password", ContentMode.PREFORMATTED);
        label.setStyleName(ValoTheme.LABEL_H2);
        TextField emailField = new TextField("EMAIL ADDRESS:");
        binder.forField(emailField).withValidator(new EmailValidator("This doesn't look like a valid email address"))
                .bind(User::getEmail, User::setEmail);
        emailField.setWidth("250");
        emailField.addStyleNames(ValoTheme.TEXTAREA_LARGE, "mystyle");
        Button resetButton = new Button("RESET");
        resetButton.setWidth("250");
        resetButton.setStyleName(ValoTheme.BUTTON_DANGER);
        Notification notification = new Notification("");
        notification.setPosition(Position.TOP_CENTER);
        resetButton.addClickListener(clickEvent -> {
            if (emailField.getValue().isEmpty()) {
                notification.show("You have to type your email!");
            } else if (Objects.isNull(userService.findUserByEmail(emailField.getValue()))) {
                notification.show("There is no such registered email!");
            } else {
                String newPassword = userService.generateNewPassword(userService.findUserByEmail(emailField.getValue()));
                try {
                    emailService.sendMail(emailField.getValue(), "NEW PASSWORD", "Your new password is: " + newPassword);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                UI.getCurrent().getNavigator().navigateTo(SignUpView.VIEW_NAME);
                notification.show("We send your email a new password, you can change it in BAG menu");
            }
        });

        addComponents(header, label, emailField, resetButton);

    }
}
