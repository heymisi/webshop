package kmihaly.mywebshop.service;

import kmihaly.mywebshop.domain.model.item.Genre;
import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.domain.model.item.SelectedItem;
import kmihaly.mywebshop.domain.model.item.Type;
import kmihaly.mywebshop.repository.ItemRepository;
import kmihaly.mywebshop.repository.SelectedItemRepository;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DAOItemService implements ItemService {

    private final ItemRepository itemRepository;

    private final SelectedItemRepository selectedItemRepository;

    public DAOItemService(ItemRepository itemRepository, SelectedItemRepository selectedItemRepository) {
        this.itemRepository = itemRepository;
        this.selectedItemRepository = selectedItemRepository;
    }

    @Override
    public List<Item> listItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item addItem(Item item) {
//        if (Objects.isNull(item) || (repository.findById(item.getId()).isPresent())) {
//            throw new IllegalArgumentException("hibás bemenet!");
//        }
        return itemRepository.save(item);
    }

    @Override
    public void changeItem(Item newItem) {
//        if (Objects.isNull(newItem) || (repository.findById(newItem.getId()).isPresent())) {
//            throw new IllegalArgumentException("hibás bemenet!");
//        }
        itemRepository.save(newItem);
    }

    @Override
    public void deleteItem(Item item) {
        if (Objects.isNull(item) || !(itemRepository.findById(item.getId()).isPresent())) {
            throw new IllegalArgumentException("hibás bemenet!");
        }
        itemRepository.delete(item);

    }

    @Override
    public List<Item> searchItemByPrice(int price) {
        if (price <= 0) {
            throw new IllegalArgumentException("az ár nem lehet 0 vagy annál kisebb!");
        }
        return itemRepository.findByPriceLessThan(price);
    }

    @Override
    public Optional<Item> searchItemById(long id) {
        return itemRepository.findById(id);
    }

    @Override
    public List<Item> searchItemByBrand(String brand) {
        if (Objects.isNull(brand)) {
            throw new IllegalArgumentException("hibás bemenet!");
        }
        return itemRepository.findByBrand(brand);
    }

    @Override
    public List<Item> getRandomItems(int size) {
        List<Item> allItems = itemRepository.findAll();
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
    public List<Item> searchByGenre(Genre genreType) {
        return itemRepository.findByGenre(genreType);
    }

    @Override
    public List<Item> searchByGenreAndBrand(Genre genreType, String brand) {
        return itemRepository.findByGenreAndBrand(genreType, brand);
    }

    @Override
    public List<Item> searchByType(Type type) {
        return itemRepository.findByType(type);
    }

    @Override
    public List<Item> multipleSearch(String name, String genre, String brand, String type) {

        List<Predicate<Item>> predicates = new ArrayList<>();
        predicates.add(item -> item.getName().equals(name) || name == "");
        predicates.add(item -> item.getGenre().toString().equals(genre) || genre == null);
        predicates.add(item -> item.getBrand().toString().equals(brand) || brand == null);
        predicates.add(item -> item.getType().toString().equals(type) || type == null);

        List<Item> filtered = itemRepository.findAll().stream()
                .filter(predicates.stream().reduce(is -> true, Predicate::and))
                .collect(Collectors.toList());
        return filtered;
    }

    @Override
    public Boolean isSelected(Set<Item> item) {
        for (SelectedItem si : selectedItemRepository.findAll()) {
            for (Item i : item) {
                if (i.equals(si.getItem())) {
                    return true;
                }
            }
        }
        return false;
    }

}