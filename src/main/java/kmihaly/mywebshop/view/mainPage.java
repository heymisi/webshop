package kmihaly.mywebshop.view;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@DesignRoot
@SpringView(name = "")
public class mainPage extends VerticalLayout implements View {
    public mainPage(){

        Label label = new Label("Thats a main menu");
        addComponent(label);
    }

}

