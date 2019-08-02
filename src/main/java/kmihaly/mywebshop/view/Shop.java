package kmihaly.mywebshop.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.service.DAOItemService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = Shop.VIEW_NAME)
public class Shop extends VerticalLayout implements View {
    public static final String VIEW_NAME = "shop";
    @Autowired
    private DAOItemService daoItemService;
    private Grid<Item> items = new Grid<>(Item.class);


    @PostConstruct
    void init(){
        items.setItems(daoItemService.listItems());
        items.setSizeFull();
        setSizeFull();
        addComponent(items);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event){
    }
}
