//package kmihaly.mywebshop.service;
//
//import kmihaly.mywebshop.domain.model.item.Item;
//import kmihaly.mywebshop.domain.model.item.SelectedItem;
//import kmihaly.mywebshop.domain.model.item.Purchase;
//import kmihaly.mywebshop.domain.model.user.User;
//import kmihaly.mywebshop.domain.model.user.UserType;
//import kmihaly.mywebshop.repository.PurchaseRepository;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//import static org.assertj.core.api.Java6Assertions.assertThat;
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
//
//@RunWith(MockitoJUnitRunner.class)
//public class DAOPurchaseServiceTest {
//
//    @Mock
//    private PurchaseRepository repository;
//    @InjectMocks
//    private DAOPurchaseService service;
//
//    private Purchase Dummy(){
//        return new Purchase();
//    }
//    private SelectedItem Dummy2(){
//        return new SelectedItem(new Item("name2", "decr2",
//                "brand2", 1, 2),2);
//    }
//
//    @Test
//    public void test_list_all_elements() {
//
//        //
//        List<SelectedItem> expected = new ArrayList<>();
//        expected.add(Dummy());
//        expected.add(Dummy2());
//        when(repository.findAll()).thenReturn(expected);
//        //
//        List<Item> result = service.listItems();
//        assertEquals(2, result.size());
//        assertThat(result).isEqualTo(expected);
//        verify(repository, times(1)).findAll();
//
//    }
///*
//    @Test
//    public void create_method_test() {
//        //
//        Purchase purchaseAfterAdd = service.create(pur1);
//        //
//        assertThat(purchaseAfterAdd).isEqualTo(pur1);
//    }
//
//    @Test
//    public void delete_an_item_from_storage_test(){
//        //
//        pur1.getUser().getStorage().getItems().add(item1);
//        pur1.getUser().getStorage().getItems().add(item2);
//        //
//        service.deleteItemFromStorage(item2,pur1.getUser());
//
//        List<SelectedItem> expected = Arrays.asList(item1);
//        List<SelectedItem> actual = pur1.getUser().getStorage().getItems();
//        //
//        assertArrayEquals(expected.toArray(), actual.toArray());
//
//    }
//
//    @Test
//    public void add_an_item_to_storage_Test(){
//        //
//        service.addItemToStorage(item1.getItem(),item1.getQuantity(),pur1.getUser());
//        service.addItemToStorage(item2.getItem(),item1.getQuantity(),pur1.getUser());
//
//        List<SelectedItem> expected = Arrays.asList(item1,item2);
//        List<SelectedItem> actual = pur1.getUser().getStorage().getItems();
//
//        //
//        assertArrayEquals(expected.toArray(),actual.toArray());
//    }
//
//    @Test
//    public void purchase_items_from_the_storage_test(){
//
//        service.purchaseItemsFromStorage(pur1.getUser());
//
//        assertTrue(pur1.getUser().getStorage().getItems().isEmpty());
//        assertTrue(pur1.getUser().getStorage().getItemsPrice() == 0);
//
//        for(SelectedItem oitem : pur1.getUser().getStorage().getItems()) {
//            assertTrue(oitem.getItem().getAvailableQuantity() == 1);
//        }
//    }*/
//
//}