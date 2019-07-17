package domain.itemmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Storage osztály szolgál a userek által kosárba tett termékekről, azok mennyiségéről
 * valamint ezek össz áráról tárol adatokat
 */
public class Storage {
    private List<OrderedItem> items;
    private int itemsPrice;

    public List<OrderedItem> getItems() {
        return items;
    }

    public int getItemsPrice() {
        return itemsPrice;
    }

    public void setItemsPrice(int itemsPrice) {
        this.itemsPrice = itemsPrice;
    }
}
