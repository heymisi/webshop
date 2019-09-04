package kmihaly.mywebshop.service;

import kmihaly.mywebshop.domain.model.item.Genre;
import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.domain.model.item.SelectedItem;
import kmihaly.mywebshop.domain.model.item.Type;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.repository.ItemRepository;
import kmihaly.mywebshop.repository.SelectedItemRepository;
import kmihaly.mywebshop.repository.UserRepository;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DAOItemService implements ItemService {

    private final ItemRepository itemRepository;

    private final SelectedItemRepository selectedItemRepository;

    private final UserRepository userRepository;

    public DAOItemService(ItemRepository itemRepository, SelectedItemRepository selectedItemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.selectedItemRepository = selectedItemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Item> listItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item addItem(Item item) {
        if (Objects.isNull(item) || (userRepository.findById(item.getId()).isPresent())) {
            throw new IllegalArgumentException("hibás bemenet!");
        }
        return itemRepository.save(item);
    }

    @Override
    public void changeItem(Item newItem) {
        if (Objects.isNull(newItem) || (userRepository.findById(newItem.getId()).isPresent())) {
            throw new IllegalArgumentException("hibás bemenet!");
        }
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
        return itemRepository.findByPriceLessThanEqual(price);
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
    public List<Item> multipleSearch(String name, String genre, String brand, String type, int price) {
        List<Predicate<Item>> predicates = new ArrayList<>();

        predicates.add(item -> item.getName().equals(name) || name.equals(""));
        predicates.add(item -> item.getGenre().toString().equals(genre) || Objects.isNull(genre));
        predicates.add(item -> item.getBrand().toString().equals(brand) || Objects.isNull(brand));
        predicates.add(item -> item.getType().toString().equals(type) || Objects.isNull(type));
        predicates.add(item -> item.getPrice() >= price || price == 0);

        return itemRepository.findAll().stream()
                .filter(predicates.stream().reduce(is -> true, Predicate::and))
                .collect(Collectors.toList());
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

    @Override
    public List<Item> findItemsOrderByPrice(int size) {
        ArrayList<Item> orderedItems = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            orderedItems.add(itemRepository.findByOrderByPriceAsc().get(i));
        }
        return orderedItems;
    }

    @Override
    public List<SelectedItem> findItemsByIsForBag(User user, boolean isForBag) {
        ArrayList<SelectedItem> selectedItems = new ArrayList<>();
        if (isForBag) {
            for (SelectedItem e : user.getSelectedItems()) {
                if (e.isForBag()) {
                    selectedItems.add(e);
                }
            }
        } else {
            user.getSelectedItems()
                    .forEach(e -> {
                        if (!e.isForBag()) {
                            selectedItems.add(e);
                        }
                    });
        }
        return selectedItems;
    }

    @Override
    public List<Item> findItemsOrderByRate(int size) {
        ArrayList<Item> orderedItems = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            orderedItems.add(itemRepository.findByOrderByRateDesc().get(i));
        }
        return orderedItems;
    }

    @Override
    public Item findItemByName(String name) {
        return itemRepository.findByName(name);
    }

    @Override
    public void setItemsForBag(SelectedItem items, User user, int quantity) {
        user.getSelectedItems().forEach(i -> {
            if (i.equals(items)) {
                i.setForBag(true);
                i.setQuantity(quantity);
                selectedItemRepository.save(i);
            }
        });
        userRepository.save(user);
    }

    @Override
    public void rateItem(Item item, double rate){
        System.err.println(item.getRate().getCounter()+ "-----1");
        double r = ((item.getRate().getValue() * item.getRate().getCounter()) + rate) / (item.getRate().getCounter() + 1);
        System.err.println(item.getRate().getCounter()+ "---2");

        item.getRate().setValue(r);
        System.err.println(item.getRate().getCounter()+"-----3");

        item.getRate().setCounter(item.getRate().getCounter()+ 1);
        System.err.println(item.getRate().getCounter()+"-------4");

        itemRepository.save(item);
    }

}