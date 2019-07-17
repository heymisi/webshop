package domain.service;
        import domain.itemmodel.Item;

        import java.util.Collection;
        import java.util.List;


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
    void addItem(Item item);

    /**
     * metódus amely az item árának megváltoztatására szolgál
     * @param item a termék amelynek árát megváltoztatjuk
     * @param price ez lesz a termék új ára
     */
    void changeItemPrice(Item item, int price);

    /**
     * ezzel a metódussal törölhető a meglévő termékek közül egy termék
     * @param item ez a megadott termék fog törlödni
     */
    void deleteItem(Item item);

    /**
     * metódus amely egy termék keresésére szolgál ár szerint
     * @param price a megadott ár alapján keres
     * @return visszaadja a megtalált terméket
     */
    Item searchItemByPrice(int price);

    /**
     * metódus amely egy termék keresésére szolgál név szerint
     * @param name a megadott név alapján keres
     * @return visszaadja a megtalált terméket
     */
    Item searchItemByName(String name);

    /**
     *metódus amely egy termék keresésére szolgál márka szerint
     * @param brand a megadott márka alapján keres
     * @return visszadja a megtalált terméket
     */
    List<Item> searchItemByBrand(String brand);

}
