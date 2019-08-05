package kmihaly.mywebshop.view;

import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = PasswordResetView.VIEW_NAME)
public class PasswordResetView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "reset";


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        Label header = new Label("Reset your password");
        header.addStyleName(ValoTheme.LABEL_H1);
        Label label = new Label("Type in your email address below and we'll send you\n" + "an email with instructions on how to create a new password", ContentMode.PREFORMATTED);
        TextField emailField = new TextField("EMAIL ADDRESS:");
        emailField.setWidth("210");
        Button resetButton = new Button("RESET");
        resetButton.setWidth("210");
        addComponents(header,label,emailField,resetButton);

    }
}
