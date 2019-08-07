package kmihaly.mywebshop.dao;

import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.domain.model.item.Purchase;
import kmihaly.mywebshop.domain.model.user.User;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryPurchaseDAO implements PurchaseDAO {

    private long id = 0;
    private Map<Long, Purchase> tables = new HashMap<>();

    @Override
    public Purchase create(Purchase purchase) {
        long Id = id++;
        purchase.setId(Id);
        tables.put(id, purchase);
        return purchase;
    }

    @Override
    public void update(Purchase purchase, Purchase purchase2) {
        purchase.setDate(purchase2.getDate());
    }

    @Override
    public void delete(long id) {
        tables.remove(id);
    }

    @Override
    public List<Purchase> getAll() {
        return new ArrayList<>(tables.values());
    }

    @Override
    public List<Purchase> readPurhcasesByUser(User user) {

        List<Purchase>  result = tables.values().stream()
                .filter(purchase -> purchase.getUser().equals(user))
                .collect(Collectors.toList());
        return result;
    }

   /* @Override
    public List<Purchase> readPurhcasesByItem(Item item) {

        List<Purchase> result = tables.values().stream()
                .filter(purchase -> purchase.getUser().getStorage().getItems().contains(item))
                .collect(Collectors.toList());
        return result;
    }
*/
    @Override
    public List<Purchase> readPurhcasesByDate(Date date) {

        List<Purchase> result = tables.values().stream()
                .filter(purchase -> purchase.getDate().equals(date))
                .collect(Collectors.toList());
        return result;
    }
}
