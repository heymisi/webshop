package service;

import domain.itemmodel.Item;

import java.util.Collection;
import java.util.List;

public class DAOItemService implements ItemService {


    @Override
    public Collection<Item> listItems() {
        return null;
    }

    @Override
    public void addItem(Item item) {

    }

    @Override
    public void changeItemPrice(Item item, Item newItem) {

    }

    @Override
    public void deleteItem(Item item) {

    }

    @Override
    public Item searchItemByPrice(int price) {
        return null;
    }

    @Override
    public Item searchItemByName(String name) {
        return null;
    }

    @Override
    public List<Item> searchItemByBrand(String brand) {
        return null;
    }
}
