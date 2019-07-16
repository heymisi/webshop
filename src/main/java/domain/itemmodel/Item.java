package domain.itemmodel;

public class Item {

    private int itemId;
    private String name;
    private String description;
    private String brand;
    private int price;
    private int rate;
    private int availableQuantity;
    private boolean isInStock;

    public Item(int itemId, String name, String description, String brand, int price,int rate, int availableQuantity, boolean isInStock) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.availableQuantity = availableQuantity;
        this.isInStock = isInStock;
        if(price > 0) {
            this.price = price;
        }
        if(rate >= 1 && rate <= 5){
            this.rate = rate;
        }
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }


    public int getItemId() {
        return itemId;
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

    public boolean isInStock() {
        return isInStock;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        if(price > 0) {
            this.price = price;
        }
    }

    public void setQuantity(int quantity) {
        this.availableQuantity = quantity;
    }

    public void setInStock(boolean inStock) {
        isInStock = inStock;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        if(rate >= 1 && rate <=5) {
            this.rate = rate;
        }
    }
}
