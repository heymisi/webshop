/*
package kmihaly.mywebshop.view;

import com.vaadin.data.ValueProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Optional;

@SpringView(name = ItemView.VIEW_NAME)
public class ItemView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "item";

    @PostConstruct
    void init(){
        Button button = new Button("Back");
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                getUI().getNavigator().navigateTo(ShopView.VIEW_NAME);
            }
        });

         Grid<User> users = new Grid<>(User.class);
            users.setItems(new User("usern", "firsn", "lastn", "mail", "ad", "psw", UserType.REGISTERED),new User("usern2", "firsn2", "lastn2", "mail2", "ad2", "psw2", UserType.REGISTERED));
            users.setSelectionMode(Grid.SelectionMode.SINGLE);
        ValueProvider<User,String> locationProvider = myBean -> myBean.getUserName();

          users.addSelectionListener(event -> {
            Optional<User> selected = event.getFirstSelectedItem();
              users.getSelectionModel().getFirstSelectedItem().ifPresent(item -> {
                  String username = locationProvider.apply(item);

                  System.err.println(username);

              });
        });


            users.setSizeFull();
        addComponent(users);

    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }


}
*/
