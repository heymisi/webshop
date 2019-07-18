package domain.itemmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public String toString() {
        return "Storage{" +
                "items=" + items +
                ", itemsPrice=" + itemsPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Storage storage = (Storage) o;
        return itemsPrice == storage.itemsPrice &&
                Objects.equals(items, storage.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, itemsPrice);
    }
}
