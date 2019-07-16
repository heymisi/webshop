package domain.itemmodel;

import java.util.ArrayList;
import java.util.List;

public class Bucket {
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
