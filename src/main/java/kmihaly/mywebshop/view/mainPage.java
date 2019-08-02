package kmihaly.mywebshop.view;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@DesignRoot
@SpringView(name = mainPage.VIEW_NAME)
public class mainPage extends VerticalLayout implements View {
    public static final String VIEW_NAME = "";

   @PostConstruct
    void init(){
       Label label = new Label("Thats a main menu");
       addComponent(label);
   }

   @Override
    public void enter (ViewChangeListener.ViewChangeEvent event){

   }

}

