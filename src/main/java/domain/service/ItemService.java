package domain.service;
        import domain.itemmodel.Item;


public interface ItemService {
    void addItem(Item item);
    void updateItem(Item item);
    void deleteItem(Item item);
    Item searchItemByPrice(int price);
    Item searchItemByName(String name);
    Item searchItemByBrand(String brand);
}
