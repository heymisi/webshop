package dao;

import domain.itemmodel.Item;
import domain.itemmodel.Purchase;
import domain.usermodel.User;

import java.util.Collection;
import java.util.Date;

public class InMemoryPurchaseDAO extends GenericDAO<Purchase> implements PurchaseDAO {
    @Override
    void create(Purchase purchase) {
        super.create(purchase);
    }

    @Override
    void update(Purchase purchase , Purchase purchase2) {
        super.update(purchase,purchase2);
    }

    @Override
    void delete(Purchase purchase) {
        super.delete(purchase);
    }

    @Override
    Collection<Purchase> getAll() {
        return super.getAll();
    }

    @Override
    public Collection<Purchase> readPurhcasesByUser(User user) {
        return null;
    }

    @Override
    public Collection<Purchase> readPurhcasesByItem(Item item) {
        return null;
    }

    @Override
    public Collection<Purchase> readPurhcasesByDate(Date date) {
        return null;
    }
}
