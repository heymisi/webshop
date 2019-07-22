package kmihaly.mywebshop.dao;

import kmihaly.mywebshop.domain.model.item.Item;

import java.util.*;

public class InMemoryItemDAO extends GenericDAO<Item> implements ItemDAO {

    private int id = 0;
    private Map<Integer, Item> tables = new HashMap<>();

    @Override
    public Item create(Item item) {
        int Id = ++id;
        item.setId(Id);
        tables.put(id, item);
        return item;
    }

    @Override
    public void update(Item item, Item item2) {
        item.setName(item2.getName());
        item.setAvailableQuantity(item2.getAvailableQuantity());
        item.setDescription(item2.getDescription());
        item.setPrice(item2.getPrice());
        item.setRate(item2.getRate());
    }

    @Override
    public void delete(int id) {
        tables.remove(id);
    }

    @Override
    public List<Item> getAll() {
        return new ArrayList<>(tables.values());
    }

    @Override
    public List<Item> ListItemsByPrice(int price) {
        List<Item> results = new ArrayList<>();
        for (Item item : tables.values()) {
            if (item.getPrice() == price) {
                results.add(item);
            }
        }
        return results;
    }

    @Override
    public List<Item> ListItemsByBrand(String brand) {
        List<Item> results = new ArrayList<>();
        for (Item item : tables.values()) {
            if (item.getBrand().equals(brand)) {
                results.add(item);
            }
        }
        return results;
    }

    @Override
    public Item selectItemById(int id) {
        for (Item item : tables.values()) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

}
