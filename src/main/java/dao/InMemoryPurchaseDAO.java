package dao;

import domain.itemmodel.Item;
import domain.itemmodel.Purchase;
import domain.usermodel.User;

import java.util.*;

public class InMemoryPurchaseDAO extends GenericDAO<Purchase> implements PurchaseDAO {

    private int id = 0;
    private Map<Integer,Purchase> tables = new HashMap<>();

    @Override
    Purchase create(Purchase purchase) {
        int Id = id++;
        purchase.setId(Id);
        tables.put(id, purchase);
        return purchase;
    }

    @Override
    void update(Purchase purchase , Purchase purchase2) {
        purchase.setDate(purchase2.getDate());
    }

    @Override
    void delete(Purchase purchase) {
        tables.remove(purchase.getId());
    }

    @Override
    Collection<Purchase> getAll() {
        return new ArrayList<>(tables.values());
    }

    @Override
    public Collection<Purchase> readPurhcasesByUser(User user) {
        List<Purchase> results = new ArrayList<>();
        for(Purchase purchase : tables.values()){
            if(purchase.getUser().equals(user)){
                results.add(purchase);
            }
        }
        return results;
    }

    @Override
    public Collection<Purchase> readPurhcasesByItem(Item item) {
        List<Purchase> results = new ArrayList<>();
        for(Purchase purchase : tables.values()){
            if(purchase.getUser().getStorage().getItems().contains(item)){
                results.add(purchase);
            }
        }
        return results;        }

    @Override
    public Collection<Purchase> readPurhcasesByDate(Date date) {
        List<Purchase> results = new ArrayList<>();
        for(Purchase purchase : tables.values()){
            if(purchase.getDate().equals(date)){
                results.add(purchase);
            }
        }
        return results;    }
}
