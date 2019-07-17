package domain.itemmodel;

/**
 * Az OrderedItem az Item osztály egy kiterjesztése
 * annyiban egészíti ki azt hogy a rendelésnél kiválasztott mennyiségét is eltárolja a termékkel együtt
 *
 */
public class OrderedItem {
    private Item item;
    private int quantity;

    public OrderedItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
