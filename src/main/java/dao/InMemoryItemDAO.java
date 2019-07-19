package dao;

import domain.itemmodel.Item;

import java.util.Collection;

public class InMemoryItemDAO implements ItemDAO {

    @Override
    public Collection<Item> ListItemsByPrice(int price) {
        return null;
    }

    @Override
    public Collection<Item> ListItemsByBrand(String brand) {
        return null;
    }

    @Override
    public Item selectItemByName(String name) {
        return null;
    }
}
