package domain.service;


import domain.itemmodel.OrderedItem;
import domain.itemmodel.Purchase;
import domain.usermodel.User;

public interface PurchaseService {
    void addItemToBucket(OrderedItem item, int amount, User user);
    void deleteItemFromBucket(OrderedItem item,int amount, User user);
    void purchaseItemsFromBucket(Purchase purchase);

}
