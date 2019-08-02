package kmihaly.mywebshop.service;

import kmihaly.mywebshop.domain.model.item.GenreType;
import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertNotNull;

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
        if((repository.findById(item.getId()).isPresent())) {throw new IllegalArgumentException();}
        return repository.save(item);
    }

    @Override
    public void changeItem(Item newItem) {
        assertNotNull(repository.findById(newItem.getId()));
        repository.save(newItem);
    }

    @Override
    public void deleteItem(Item item) {
        assertNotNull(repository.findById(item.getId()));
        repository.delete(item);
    }

    @Override
    public List<Item> searchItemByPrice(int price) {
        return repository.findByPrice(price);
    }

    @Override
    public Optional<Item> searchItemById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<Item> searchItemByBrand(String brand) {
        return repository.findByBrand(brand);
    }

    @Override
    public List<Item> getRandomItems(int amount) {
        List<Item> allItems = repository.findAll();
        if (allItems.size() < amount) {
            throw new IllegalArgumentException();
        } else {
            List<Item> randomItems = new ArrayList<>();
            Collections.shuffle(allItems);
            for (int i = 0; i < amount; i++) {
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

}
