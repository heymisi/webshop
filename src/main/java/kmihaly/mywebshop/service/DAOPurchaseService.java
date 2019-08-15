package kmihaly.mywebshop.service;

import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.domain.model.item.Purchase;
import kmihaly.mywebshop.domain.model.item.SelectedItem;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.repository.PurchaseRepository;
import kmihaly.mywebshop.repository.SelectedItemRepository;
import kmihaly.mywebshop.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class DAOPurchaseService implements PurchaseService {

    private final PurchaseRepository purchaseRepository;

    private final UserRepository userRepository;

    private final SelectedItemRepository selectedItemRepository;



    public DAOPurchaseService(PurchaseRepository purchaseRepository, UserRepository userRepository, SelectedItemRepository selectedItemRepository) {
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.selectedItemRepository = selectedItemRepository;
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
    public void addItemToStorage(Item item, int orderedQuantity, User user) {
        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("üres user!");
        } else if (Objects.isNull(item)) {
            throw new IllegalArgumentException("üres item!");
        } else if (!(userRepository.findById(user.getId()).isPresent())) {
            throw new IllegalArgumentException("nincs ilyen user!");
        } else if (orderedQuantity <= 0) {
            throw new IllegalArgumentException("nem jó a rendelés mennyiség!");
        } else {

            SelectedItem selectedItem = new SelectedItem(item, orderedQuantity);
           // selectedItem = selectedItemRepository.save(selectedItem);
            user.addItem(selectedItem);
            userRepository.save(user);
        }
    }

    @Override
    public void deleteItemFromStorage(SelectedItem item, User user) {
        if (Objects.isNull(user) || !(userRepository.findById(user.getId()).isPresent()) || Objects.isNull(item)) {
            throw new IllegalArgumentException("hibás bemenet!");
        }
        user.getSelectedItems().remove(item);
        user.setSelectedItems(user.getSelectedItems());
        userRepository.save(user);
        selectedItemRepository.delete(item);
    }

    @Override
    public void purchaseItemsFromStorage(User user) {
        if (Objects.isNull(user) || !(userRepository.findById(user.getId()).isPresent())) {
            throw new IllegalArgumentException("hibás bemenet!");
        }
        Purchase purchase = new Purchase(user, new Date());
        purchaseRepository.save(purchase);
//
//        for(SelectedItem items : user.getSelectedItems()){
//            selectedItemRepository.delete(items);
//            System.err.println(items);
//        }
        user.getSelectedItems().clear();
        userRepository.save(user);


        user.getSelectedItems().stream().forEach(s -> {
            s.getItem().setAvailableQuantity(s.getItem().getAvailableQuantity() - 1);
        });
    }

    public int getSelectedItemsPrice(User user){
        int price = 0;
        for(SelectedItem items : user.getSelectedItems()){
           price += items.getItem().getPrice() * items.getQuantity();
        }
        return price;
    }
}
