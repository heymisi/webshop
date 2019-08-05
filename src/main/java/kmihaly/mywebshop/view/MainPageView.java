package kmihaly.mywebshop.view;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

@DesignRoot
@SpringView(name = MainPageView.VIEW_NAME)
public class MainPageView extends VerticalLayout implements View {
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

