package kmihaly.mywebshop.service;

import kmihaly.mywebshop.dao.InMemoryPurchaseDAO;
import kmihaly.mywebshop.domain.model.item.OrderedItem;
import kmihaly.mywebshop.domain.model.item.Purchase;
import kmihaly.mywebshop.domain.model.user.User;

import java.util.Collection;

public class DAOPurchaseService implements PurchaseService{
    InMemoryPurchaseDAO dao = new InMemoryPurchaseDAO();

    @Override
    public Collection<Purchase> listPurchases() {
        return dao.getAll();
    }

    @Override
    public void addItemToStorage(OrderedItem item, User user) {
        user.getStorage().getItems().add(item);
    }

    @Override
    public void deleteItemFromStorage(OrderedItem item, User user) {
        user.getStorage().getItems().remove(item);
    }

    @Override
    public void purchaseItemsFromStorage(Purchase purchase) {
        dao.create(purchase);
        purchase.getUser().getStorage().setItems(null);
        purchase.getUser().getStorage().setItemsPrice(0);
    }
}
