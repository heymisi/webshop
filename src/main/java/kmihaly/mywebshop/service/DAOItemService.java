package kmihaly.mywebshop.service;

import kmihaly.mywebshop.domain.model.item.GenreType;
import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.repository.ItemRepository;

import java.util.*;

public class DAOItemService implements ItemService {

    private final ItemRepository repository;

    public DAOItemService(ItemRepository itemRepository) {
        repository = itemRepository;
    }

    @Override
    public List<Item> listItems() {
        return repository.findAll();
    }

    @Override
    public Item addItem(Item item) {
        if (Objects.isNull(item) || (repository.findById(item.getId()).isPresent())) {
            throw new IllegalArgumentException("hibás bemenet!");
        }
        return repository.save(item);
    }

    @Override
    public void changeItem(Item newItem) {
        if (Objects.isNull(newItem) || (repository.findById(newItem.getId()).isPresent())) {
            throw  new IllegalArgumentException("hibás bemenet!");
        }
        repository.save(newItem);
    }

    @Override
    public void deleteItem(Item item) {
        if (Objects.isNull(item) || !(repository.findById(item.getId()).isPresent())) {
            throw new IllegalArgumentException("hibás bemenet!");
        }
        repository.delete(item);

    }

    @Override
    public List<Item> searchItemByPrice(int price) {
        if (price <= 0){
            throw new IllegalArgumentException("az ár nem lehet 0 vagy annál kisebb!");
        }
        return repository.findByPriceLessThan(price);
    }

    @Override
    public Optional<Item> searchItemById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<Item> searchItemByBrand(String brand) {
        if(Objects.isNull(brand)){
            throw new IllegalArgumentException("hibás bemenet!");
        }
        return repository.findByBrand(brand);
    }

    @Override
    public List<Item> getRandomItems(int size) {
        List<Item> allItems = repository.findAll();
        if (allItems.size() < size) {
            throw new IllegalArgumentException("nincs ennyi elem!");
        } else {
            List<Item> randomItems = new ArrayList<>();
            Collections.shuffle(allItems);
            for (int i = 0; i < size; i++) {
                Item item = allItems.get(i);
                randomItems.add(item);
            }
            return randomItems;
        }
    }

    @Override
    public List<Item> searchByGenre(GenreType genreType) {
        return repository.findByGenre(genreType);
    }

    @Override
    public List<Item> searchByGenreAndBrand(GenreType genreType,String brand) {
        return repository.findByGenreAndBrand(genreType,brand);
    }
}
