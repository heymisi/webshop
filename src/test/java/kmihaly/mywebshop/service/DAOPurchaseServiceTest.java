package kmihaly.mywebshop.service;

import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.domain.model.item.OrderedItem;
import kmihaly.mywebshop.domain.model.item.Purchase;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import org.junit.Test;
import org.mockito.internal.matchers.Or;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class DAOPurchaseServiceTest {

    DAOPurchaseService service = new DAOPurchaseService();
    Purchase pur1 = new Purchase(new User("nickn1", "Nagy1", "Bela1", "a@mail1",
            "Bp1", "1231", UserType.REGISTERED), new Date(2015, 01, 12));
    Purchase pur2 = new Purchase(new User("nickn2", "Nagy2", "Bela2", "a@mail2",
            "Bp2", "1232", UserType.REGISTERED), new Date(2015, 01, 12));
    Purchase pur3 = new Purchase(new User("nickn2", "Nagy2", "Bela2", "a@mail2",
            "Bp2", "1232", UserType.REGISTERED), new Date(2015, 01, 15));


    OrderedItem item1 = new OrderedItem(new Item("name1", "decr1",
            "brand1", 1, 2),2);
    OrderedItem item2 = new OrderedItem(new Item("name2", "decr2",
            "brand2", 1, 2),2);
    @Test
    public void returnAll() {
        //GIVEN
        service.create(pur1);
        service.create(pur2);
        //WHEN
        List<Purchase> all = service.listPurchases();
        //THEN
        assertThat(all).containsExactly(pur1, pur2);
    }

    @Test
    public void create_method_test() {
        //
        Purchase purchaseAfterAdd = service.create(pur1);
        //
        assertThat(purchaseAfterAdd).isEqualTo(pur1);
    }

    @Test
    public void delete_an_item_from_storage_test(){
        //
        pur1.getUser().getStorage().getItems().add(item1);
        pur1.getUser().getStorage().getItems().add(item2);
        //
        service.deleteItemFromStorage(new OrderedItem(new Item("name2", "decr2",
                "brand2", 1, 1),2),pur1.getUser());

        List<OrderedItem> expected = Arrays.asList(item1);
        List<OrderedItem> actual = pur1.getUser().getStorage().getItems();
        //
        assertArrayEquals(expected.toArray(), actual.toArray());

    }

    @Test
    public void add_an_item_to_storage_Test(){
        //
        service.addItemToStorage(item1,pur1.getUser());
        service.addItemToStorage(item2,pur1.getUser());

        List<OrderedItem> expected = Arrays.asList(item1,item2);
        List<OrderedItem> actual = pur1.getUser().getStorage().getItems();

        //
        assertArrayEquals(expected.toArray(),actual.toArray());
    }

    @Test
    public void purchase_items_from_the_storage_test(){

        service.purchaseItemsFromStorage(pur1.getUser());

        assertTrue(pur1.getUser().getStorage().getItems().isEmpty());
        assertTrue(pur1.getUser().getStorage().getItemsPrice() == 0);
        for(OrderedItem oitem :pur1.getUser().getStorage().getItems()) {
            assertTrue(oitem.getItem().getAvailableQuantity() == 1);
        }
    }

}