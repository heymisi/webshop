package domain.itemmodel;

import java.util.Objects;

/**
 * Az Item osztály a terméket reprezentálja
 * rendelkezik egyedi azonosítóval a megkülönböztehetőség végett
 * tároljuk a hozzátartozó tulajdonságokat, valamint azt hogy rendelkezésre áll e jelenleg
 */
public class Item {

    private int id;
    private String name;
    private String description;
    private String brand;
    private int price;
    private int rate;
    private int availableQuantity;
    private SizeType size;



    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return availableQuantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) { this.price = price; }

    public void setQuantity(int quantity) {
        this.availableQuantity = quantity;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) { this.rate = rate; }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public SizeType getSize() {
        return size;
    }

    public void setSize(SizeType size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", rate=" + rate +
                ", availableQuantity=" + availableQuantity +
                ", size=" + size +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id &&
                price == item.price &&
                rate == item.rate &&
                availableQuantity == item.availableQuantity &&
                Objects.equals(name, item.name) &&
                Objects.equals(description, item.description) &&
                Objects.equals(brand, item.brand) &&
                size == item.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, brand, price, rate, availableQuantity, size);
    }
}
