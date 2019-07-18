package domain.dao;

import domain.itemmodel.Item;
import domain.itemmodel.Purchase;
import domain.usermodel.User;

import java.util.Collection;
import java.util.Date;

public interface PurchaseDAO {

    void createPurhcase(Purchase purhcase);

    void updatePurhcase(Purchase purhcase);

    void deletePurhcase(Purchase purhcase);

    Collection<Purchase> readPurchases();

    Collection<Purchase> readPurhcasesByUser(User user);

    Collection<Purchase> readPurhcasesByItem(Item item);

    Collection<Purchase> readPurhcasesByDate(Date date);
}
