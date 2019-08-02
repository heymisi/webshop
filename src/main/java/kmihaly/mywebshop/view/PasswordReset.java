package kmihaly.mywebshop.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = PasswordReset.VIEW_NAME)
public class PasswordReset extends VerticalLayout implements View {
    public static final String VIEW_NAME = "reset";


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Label label = new Label("Reset your password");
        TextArea area = new TextArea("");
        addComponent(label);
    }
}
