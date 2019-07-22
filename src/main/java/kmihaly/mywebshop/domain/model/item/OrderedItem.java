package kmihaly.mywebshop.domain.model.item;

import java.util.Objects;

/**
 * Az OrderedItem az Item osztály egy kiterjesztése
 * annyiban egészíti ki azt hogy a rendelésnél kiválasztott mennyiségét is eltárolja a termékkel együtt
 *
 */
public class OrderedItem {
    private Item item;
    private int quantity;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderedItem that = (OrderedItem) o;
        return quantity == that.quantity &&
                Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, quantity);
    }

    @Override
    public String toString() {
        return "OrderedItem{" +
                "item=" + item +
                ", quantity=" + quantity +
                '}';
    }
}
