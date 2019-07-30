package kmihaly.mywebshop.service;

import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.domain.model.item.SelectedItem;
import kmihaly.mywebshop.domain.model.item.Purchase;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.repository.PurchaseRepository;
import static junit.framework.TestCase.assertNotNull;

import java.util.Date;
import java.util.List;

public class DAOPurchaseService implements PurchaseService {

    private final PurchaseRepository purchaseRepository;

    public DAOPurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public Purchase create(Purchase purchase) {

        return purchaseRepository.save(purchase);

    }

    @Override
    public List<Purchase> listPurchases() {
        return purchaseRepository.findAll();
    }

    @Override
    public void addItemToStorage(Item item, int orderedQuantity, User user) {

        assertNotNull(item);
        assertNotNull(user);

        user.getStorage().getItems().add(new SelectedItem(item, orderedQuantity));
    }

    @Override
    public void deleteItemFromStorage(SelectedItem item, User user) {

        assertNotNull(item);
        assertNotNull(user);

        user.getStorage().getItems().remove(item);
    }

    @Override
    public void purchaseItemsFromStorage(User user) {
        assertNotNull(user);

        Purchase purchase = new Purchase(user, new Date());
        purchaseRepository.save(purchase);

        user.getStorage().getItems().clear();
        user.getStorage().setItemsPrice(0);

        user.getStorage().getItems().stream()
                .map(s -> s.getQuantity() - 1);
    }
}
