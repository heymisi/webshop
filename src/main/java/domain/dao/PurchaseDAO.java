package domain.dao;

import domain.itemmodel.Item;
import domain.itemmodel.Purchase;
import domain.usermodel.User;

import java.util.Collection;
import java.util.Date;

public interface PurchaseDAO {
    /**
     * új vásárlás létrehozására szolgáló metódus
     * @param purchase a kapott felhasználót hozza létre
     */
    void createPurhcase(Purchase purchase);
    /**
     * vásárlás modósítására szolgáló metódus
     * @param purchase a kapott vásárlás modosítja
     */

    void updatePurhcase(Purchase purchase);
    /**
     * vásárlás törlésére szolgáló metódus
     * @param purhcase a kapott vásárlást törli
     */
    void deletePurhcase(Purchase purhcase);

    /**
     * a vásárlások beolvasására szolgaláó metótus
     * @return az összes vásárlást tartalmazó listájával tér vissza
     */
    Collection<Purchase> readPurchases();
    /**
     * a vásárlás felhasználó szerint történő listázása
     * @param user a felhasználó amely szerint történik a listázás
     * @return visszatér a kapott vásárlások listájával
     */
    Collection<Purchase> readPurhcasesByUser(User user);

    /**
     * a vásárlás termék szerint történő listázása
     * @param item a termék amely szerint történik a listázás
     * @return visszatér a kapott vásárlások listájával
     */
    Collection<Purchase> readPurhcasesByItem(Item item);

    /**
     * a vásárlás dátum szerint történő listázása
     * @param date a dátum amely szerint történik a listázás
     * @return visszatér a kapott vásárlások listájával
     */
    Collection<Purchase> readPurhcasesByDate(Date date);
}
