package kmihaly.mywebshop.service;

import kmihaly.mywebshop.dao.InMemoryItemDAO;
import kmihaly.mywebshop.domain.model.item.Item;

import java.util.Collection;

public class DAOItemService implements ItemService {

    private InMemoryItemDAO dao = new InMemoryItemDAO();

    @Override
    public Collection<Item> listItems() {
        return dao.getAll();
    }

    @Override
    public Item addItem(Item item) {
        return dao.create(item);
    }

    @Override
    public void changeItem(Item item, Item newItem) {
         dao.update(item,newItem);
    }

    @Override
    public void deleteItem(int id) {
        dao.delete(id);
    }

    @Override
    public Collection<Item> searchItemByPrice(int price) {
        return dao.ListItemsByPrice(price);
    }

    @Override
    public Item searchItemById(int id) {
        return dao.selectItemById(id);
    }

    @Override
    public Collection<Item> searchItemByBrand(String brand) {
        return dao.ListItemsByBrand(brand);
    }
}
