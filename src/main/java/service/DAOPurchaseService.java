package service;

import domain.itemmodel.OrderedItem;
import domain.itemmodel.Purchase;
import domain.usermodel.User;

import java.util.Collection;

public class DAOPurchaseService implements PurchaseService{
    @Override
    public Collection<Purchase> listPurchases() {
        return null;
    }

    @Override
    public void addItemToStorage(OrderedItem item, User user) {

    }

    @Override
    public void deleteItemFromStorage(OrderedItem item, User user) {

    }

    @Override
    public Purchase purchaseItemsFromStorage(User user) {
        return null;
    }
}
