package dao;

import domain.itemmodel.Item;

import java.util.*;

public class InMemoryItemDAO extends GenericDAO<Item> implements ItemDAO {

    private int id = 0;
    private Map<Integer, Item> tables = new HashMap<>();

    @Override
    Item create(Item item) {
        int Id = id++;
        item.setId(Id);
        tables.put(id, item);
        return item;
    }

    @Override
    void update(Item item, Item item2) {
        item.setAvailableQuantity(item2.getAvailableQuantity());
        item.setDescription(item2.getBrand());
        item.setPrice(item2.getPrice());
        item.setRate(item2.getRate());
    }

    @Override
    void delete(Item item) {
        tables.remove(item.getId());
    }

    @Override
    Collection<Item> getAll() {
        return new ArrayList<>(tables.values());
    }

    @Override
    public Collection<Item> ListItemsByPrice(int price) {
        List<Item> results = new ArrayList<>();
        for(Item item : tables.values()){
            if(item.getPrice() == price){
                results.add(item);
            }
        }
        return results;
    }

    @Override
    public Collection<Item> ListItemsByBrand(String brand) {
        List<Item> results = new ArrayList<>();
        for(Item item : tables.values()){
            if(item.getBrand().equals(brand)){
                results.add(item);
            }
        }
        return results;
    }

    @Override
    public Item selectItemById(int id) {
        Item result = new Item();
        for (Item item : tables.values()) {
            if (item.getId() == id) {
                result = item;
            }
        }
        return result;
    }

}
