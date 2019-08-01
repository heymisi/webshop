package kmihaly.mywebshop.view;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.repository.ItemRepository;
import kmihaly.mywebshop.service.DAOItemService;
import kmihaly.mywebshop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@SpringView(name = "shop")
public class Bag extends VerticalLayout implements View {
    @Autowired
    private DAOItemService daoItemService;

    public ItemService itemService(ItemRepository itemRepository) {
        return new DAOItemService(itemRepository);
    }

    private Grid<Item> users = new Grid<>(Item.class);

    public Bag() {
        users.setItems(daoItemService.listItems());
        addComponent(users);

    }
}


