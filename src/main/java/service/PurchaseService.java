package service;


import domain.itemmodel.OrderedItem;
import domain.itemmodel.Purchase;
import domain.usermodel.User;

import java.util.Collection;

public interface PurchaseService {

    /**
     * kilistázza az összes vásárlást
     * @return visszadja a vásárlások listáját
     */
    Collection<Purchase> listPurchases();

    /**
     * metódus amely hozzáadja a kiválasztott terméket a kosárhoz
     * @param item a termék amelyet hozzáadunk a kosárhoz
     * @param user a felhasználó akinek a kosarához hozzáadjuk a terméket
     */
    void addItemToStorage(OrderedItem item, User user);

    /**
     * ezzel a metódussal törölhetünk már hozzáadott terméket a kosárból
     * @param item termék amelyet törölünk a kosárból
     * @param user a felhasználó akinek a kosarából törüljük a terméket
     */
    void deleteItemFromStorage(OrderedItem item, User user);

    /**
     *a kosárban lévő termékek megvásárlására szolgáló metódus
     * @param user a felhasználó aki megvásárolja a kosarában lévő termékeit
     * @return visszaadja a megtörént vásárlást
     */
    Purchase purchaseItemsFromStorage(User user);

}
