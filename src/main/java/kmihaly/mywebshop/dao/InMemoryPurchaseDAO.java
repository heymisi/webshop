package kmihaly.mywebshop.dao;

import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.domain.model.item.Purchase;
import kmihaly.mywebshop.domain.model.user.User;

import java.util.*;

public class InMemoryPurchaseDAO extends GenericDAO<Purchase> implements PurchaseDAO {

    private int id = 0;
    private Map<Integer, Purchase> tables = new HashMap<>();

    @Override
    public Purchase create(Purchase purchase) {
        int Id = id++;
        purchase.setId(Id);
        tables.put(id, purchase);
        return purchase;
    }

    @Override
    public void update(Purchase purchase, Purchase purchase2) {
        purchase.setDate(purchase2.getDate());

    }

    @Override
    public void delete(int id) {
        tables.remove(id);
    }

    @Override
    public List<Purchase> getAll() {
        return new ArrayList<>(tables.values());
    }

    @Override
    public List<Purchase> readPurhcasesByUser(User user) {
        List<Purchase> results = new ArrayList<>();
        for (Purchase purchase : tables.values()) {
            if (purchase.getUser().equals(user)) {
                results.add(purchase);
            }
        }
        return results;
    }

    @Override
    public List<Purchase> readPurhcasesByItem(Item item) {
        List<Purchase> results = new ArrayList<>();
        for (Purchase purchase : tables.values()) {
            if (purchase.getUser().getStorage().getItems().contains(item)) {
                results.add(purchase);
            }
        }
        return results;
    }

    @Override
    public List<Purchase> readPurhcasesByDate(Date date) {
        List<Purchase> results = new ArrayList<>();
        for (Purchase purchase : tables.values()) {
            if (purchase.getDate().equals(date)) {
                results.add(purchase);
            }
        }
        return results;
    }
}
