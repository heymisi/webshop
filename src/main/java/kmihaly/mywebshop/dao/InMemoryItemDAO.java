package kmihaly.mywebshop.dao;

import kmihaly.mywebshop.domain.model.item.Item;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryItemDAO  implements ItemDAO {

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

        List<Item> list = tables.values().stream()
                .filter(s -> s.getPrice() == price)
                .collect(Collectors.toList());

        return list;
    }

    @Override
    public List<Item> ListItemsByBrand(String brand) {

        List<Item> result = tables.values().stream()
                .filter(i -> i.getBrand().equals(brand))
                .collect(Collectors.toList());

        return result;
    }


    @Override
    public Item selectItemById(int id) {

        Item result = tables.values().stream()
                .filter(item -> item.getId() == id)
                .findAny()
                .orElse(null);

        return result;
    }

}
