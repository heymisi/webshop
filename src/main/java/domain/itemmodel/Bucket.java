package domain.itemmodel;

import java.util.Vector;

public class Bucket {
    private Vector<OrderedItem> items = new Vector();
    private int itemsPrice;

    public Vector<OrderedItem> getItems() {
        return items;
    }

    public int getItemsPrice() {
        return itemsPrice;
    }

    public void setItemsPrice(int itemsPrice) {
        this.itemsPrice = itemsPrice;
    }
}
