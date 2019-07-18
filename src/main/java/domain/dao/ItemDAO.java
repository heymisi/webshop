package domain.dao;

import domain.itemmodel.Item;

import java.util.Collection;

public interface ItemDAO {
    /**
     * új termék létrehozására szolgáló metódus
     * @param item a kapott termék hozza létre
     */
    void createItem(Item item);

    /**
     * termék modósítására szolgáló metódus
     * @param item a kapott termék modosítja
     */
    void updateItem(Item item);

    /**
     * termék törlésére szolgáló metódus
     * @param item a kapott terméket törli
     */
    void deleteItem(Item item);

    /**
     * a termék beolvasására szolgaláó metótus
     * @return az összes termékeket tartalmazó listájával tér vissza
     */
    Collection<Item> readItems();

    /**
     * a termék ár szerint történő listázása
     * @param price az ár amely szerint történik az szelektálás
     * @return visszatér a kapott termékek listájával
     */
    Collection<Item> readItemsByPrice(int price);
    /**
     * a termék márka szerint történő listázása
     * @param brand a márka amely szerint történik az szelektálás
     * @return visszatér a kapott termékek listájával
     */
    Collection<Item> readItemsByBrand(String brand);

    /**
     * a termék név szerint történő listázása
     * @param name a név amely szerint történik az szelektálás
     * @return visszatér a kapott termékkel
     */
    Item readItemByName(String name);
}
