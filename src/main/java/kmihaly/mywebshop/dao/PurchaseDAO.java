package kmihaly.mywebshop.dao;

import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.domain.model.item.Purchase;
import kmihaly.mywebshop.domain.model.user.User;

import java.util.Date;
import java.util.List;

public interface PurchaseDAO {

    /**
     * a vásárlás felhasználó szerint történő listázása
     *
     * @param user a felhasználó amely szerint történik a listázás
     * @return visszatér a kapott vásárlások listájával
     */
    List<Purchase> readPurhcasesByUser(User user);

    /**
     * a vásárlás termék szerint történő listázása
     *
     * @param item a termék amely szerint történik a listázás
     * @return visszatér a kapott vásárlások listájával
     */
    List<Purchase> readPurhcasesByItem(Item item);

    /**
     * a vásárlás dátum szerint történő listázása
     *
     * @param date a dátum amely szerint történik a listázás
     * @return visszatér a kapott vásárlások listájával
     */
    List<Purchase> readPurhcasesByDate(Date date);
}
