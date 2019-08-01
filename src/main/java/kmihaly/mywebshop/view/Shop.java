package kmihaly.mywebshop.view;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = "bag")
public class Shop extends VerticalLayout implements View {
    public Shop(){

        Button button = new Button("Go to main view");
        addComponent(button);
    }
}
