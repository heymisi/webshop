package dao;

import domain.itemmodel.Item;

import java.util.Collection;

public interface ItemDAO {
    
    /**
     * a termék ár szerint történő listázása
     * @param price az ár amely szerint történik az szelektálás
     * @return visszatér a kapott termékek listájával
     */
    Collection<Item> ListItemsByPrice(int price);
    /**
     * a termék márka szerint történő listázása
     * @param brand a márka amely szerint történik az szelektálás
     * @return visszatér a kapott termékek listájával
     */
    Collection<Item> ListItemsByBrand(String brand);

    /**
     * a termék azonosító szerint történő listázása
     * @param id az azonosító amely szerint történik az szelektálás
     * @return visszatér a kapott termékkel
     */
    Item selectItemById(int id);

}
