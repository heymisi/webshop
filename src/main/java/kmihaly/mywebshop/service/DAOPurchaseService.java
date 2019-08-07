package kmihaly.mywebshop.service;

import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.domain.model.item.Purchase;
import kmihaly.mywebshop.domain.model.item.SelectedItem;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;


public class DAOPurchaseService implements PurchaseService {

    private final PurchaseRepository purchaseRepository;

    public DAOPurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
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
        if (Objects.isNull(user) || Objects.isNull(item) || !(purchaseRepository.findById(user.getId()).isPresent()) || orderedQuantity <= 0 ) {
            throw new IllegalArgumentException("hibás bemenet!");
        }

        user.getStorage().getItems().add(new SelectedItem(item, orderedQuantity));
    }

    @Override
    public void deleteItemFromStorage(SelectedItem item, User user) {
        if (Objects.isNull(user) || !(purchaseRepository.findById(user.getId()).isPresent()) || Objects.isNull(item)) {
            throw new IllegalArgumentException("hibás bemenet!");
        }
        user.getStorage().getItems().remove(item);
    }

    @Override
    public void purchaseItemsFromStorage(User user) {
        if (Objects.isNull(user) || !(purchaseRepository.findById(user.getId()).isPresent())) {
            throw new IllegalArgumentException("hibás bemenet!");
        }
        Purchase purchase = new Purchase(user, new Date());
        purchaseRepository.save(purchase);

        user.getStorage().getItems().clear();
        user.getStorage().setItemsPrice(0);

        for(SelectedItem item : user.getStorage().getItems()){
            item.setQuantity(item.getQuantity() - 1);
        }
    }
}
