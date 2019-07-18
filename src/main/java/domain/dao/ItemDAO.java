package domain.dao;

import domain.itemmodel.Item;

import java.util.Collection;

public interface ItemDAO {

    void createItem(Item item);

    void updateItem(Item item);

    void deleteItem(Item item);

    Collection<Item> readItems();

    Collection<Item> readItemsByPrice(int price);

    Collection<Item> readItemsByBrand(String brand);

    Item readItemByName(String name);
}
