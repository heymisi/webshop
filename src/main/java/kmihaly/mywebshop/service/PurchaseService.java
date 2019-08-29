
package kmihaly.mywebshop.service;


import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.domain.model.item.SelectedItem;
import kmihaly.mywebshop.domain.model.item.Purchase;
import kmihaly.mywebshop.domain.model.user.User;

import java.util.List;

public interface PurchaseService {
    Purchase create(Purchase purchase);


    /**
     * kilistázza az összes vásárlást
     *
     * @return visszadja a vásárlások listáját
     */

    List<Purchase> listPurchases();


    /**
     * metódus amely hozzáadja a kiválasztott terméket a kosárhoz
     *
     * @param item a termék amelyet hozzáadunk a kosárhoz
     * @param user a felhasználó akinek a kosarához hozzáadjuk a terméket
     */

    void addItemToStorage(Item item, int orderedQuantity, User user,boolean isForBag);


    /**
     * ezzel a metódussal törölhetünk már hozzáadott terméket a kosárból
     *
     * @param item termék amelyet törölünk a kosárból
     * @param user a felhasználó akinek a kosarából törüljük a terméket
     */

    void deleteItemFromStorage(SelectedItem item, User user);


    /**
     * a kosárban lévő termékek megvásárlására szolgáló metódus
     *
     * @param user a felhasználó aki megvásárolja a kosarában lévő termékeit
     * @return visszaadja a megtörént vásárlást
     */

    void purchaseItemsFromStorage(User user);



}