package kmihaly.mywebshop.service;

import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.domain.model.item.Purchase;
import kmihaly.mywebshop.domain.model.item.SelectedItem;
import kmihaly.mywebshop.domain.model.item.UserBag;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.repository.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;


public class DAOPurchaseService implements PurchaseService {

    private final PurchaseRepository purchaseRepository;

    private final UserRepository userRepository;

    private final SelectedItemRepository selectedItemRepository;

    private final ItemRepository itemRepository;

    private final DAOItemService itemService;

    private final UserBagRepository userBagRepository;


    public DAOPurchaseService(PurchaseRepository purchaseRepository, UserRepository userRepository, SelectedItemRepository selectedItemRepository, ItemRepository itemRepository, DAOItemService itemService, UserBagRepository userBagRepository) {
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.selectedItemRepository = selectedItemRepository;
        this.itemRepository = itemRepository;
        this.itemService = itemService;
        this.userBagRepository = userBagRepository;
    }

    @Override
    public Purchase create(Purchase purchase) {
        if (Objects.isNull(purchase)) {
            throw new IllegalArgumentException("hibás bemenet!");
        }
        return purchaseRepository.save(purchase);
    }

    @Override
    public List<Purchase> listPurchases() {
        return purchaseRepository.findAll();
    }

    @Override
    public void addItemToStorage(Item item, int orderedQuantity, User user, boolean isForBag) {
        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("üres user!");
        } else if (Objects.isNull(item)) {
            throw new IllegalArgumentException("üres item!");
        } else if (!(userRepository.findById(user.getId()).isPresent())) {
            throw new IllegalArgumentException("nincs ilyen user!");
        } else if (orderedQuantity <= 0) {
            throw new IllegalArgumentException("nem jó a rendelés mennyiség!");
        } else {
            UserBag bag = userBagRepository.findByUser(user);
            SelectedItem save = selectedItemRepository.save(new SelectedItem(item, orderedQuantity, isForBag));
            bag.addItem(save);
            userBagRepository.save(bag);
        }
    }

    @Override
    public void deleteItemFromStorage(SelectedItem item, User user) {
        if (Objects.isNull(user) || !(userRepository.findById(user.getId()).isPresent()) || Objects.isNull(item)) {
            throw new IllegalArgumentException("hibás bemenet!");
        }
        UserBag bag = userBagRepository.findByUser(user);
        bag.getItems().remove(item);
        System.err.println(item);
        userBagRepository.save(bag);
        selectedItemRepository.delete(item);

    }

    @Override
    public void purchaseItemsFromStorage(User user) {
        if (Objects.isNull(user) || !(userRepository.findById(user.getId()).isPresent())) {
            throw new IllegalArgumentException("hibás bemenet!");
        }

        Purchase purchase = new Purchase(user, new Date(), getSelectedItemsPrice(user));

        itemService.findItemsByIsForBag(user, true).forEach(s -> {
            s.getItem().setAvailableQuantity(s.getItem().getAvailableQuantity() - 1);
            itemRepository.save(s.getItem());
            purchase.addItem(s);
        });

        purchaseRepository.save(purchase);

        userBagRepository.findByUser(user).getItems().removeAll(itemService.findItemsByIsForBag(user,true));
        userBagRepository.save(userBagRepository.findByUser(user));

    }


    public int getSelectedItemsPrice(User user) {
        int price = 0;
        for (SelectedItem items : userBagRepository.findByUser(user).getItems()) {
            if (items.isForBag()) {
                price += items.getItem().getPrice() * items.getQuantity();
            }
        }
        return price;
    }

    public List<SelectedItem> getUserBagItems(User user){
       return userBagRepository.findByUser(user).getItems();
    }
}
