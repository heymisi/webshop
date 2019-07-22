package kmihaly.mywebshop.service;
        import kmihaly.mywebshop.domain.model.item.Item;

        import java.util.Collection;


public interface ItemService {

    /**
     * metódus amely kilistázza az összes meglévő terméket
     * @return visszatér a termékek listájával
     */
    Collection<Item> listItems();

    /**
     * metódus amely új terméket add hozzá a meglévő termékekhez
     * @param item a megadott terméket fogja hozáaddni a meglévőkhöz
     */
    Item  addItem(Item item);

    /**
     * metódus amely a termék tulaj
     * @param item a termék amelynek tulajdonságait megváltoztatjuk
     * @param newItem ez lesz az új termék
     */
    void changeItem(Item item, Item newItem);

    /**
     * ezzel a metódussal törölhető a meglévő termékek közül egy termék
     * @param item ez a megadott termék fog törlödni
     */
    void deleteItem(int id);

    /**
     * metódus amely egy termék keresésére szolgál ár szerint
     * @param price a megadott ár alapján keres
     * @return visszaadja a megtalált terméket
     */
    Collection<Item> searchItemByPrice(int price);

    /**
     * metódus amely egy termék keresésére szolgál azonosító szerint
     * @param id a megadott azonosító alapján keres
     * @return visszaadja a megtalált terméket
     */
    Item searchItemById(int id);

    /**
     *metódus amely egy termék keresésére szolgál márka szerint
     * @param brand a megadott márka alapján keres
     * @return visszadja a megtalált terméket
     */
    Collection<Item> searchItemByBrand(String brand);

}
