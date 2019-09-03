package kmihaly.mywebshop.service;

import kmihaly.mywebshop.domain.model.item.Brand;
import kmihaly.mywebshop.domain.model.item.Genre;
import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.domain.model.item.Type;
import kmihaly.mywebshop.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.*;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DAOItemServiceTest {

    @InjectMocks
    private DAOItemService service;
    @Mock
    private ItemRepository repository;

    private Item DummyItem() {
        return new Item("name1", "desc1", Brand.ADIDAS, 1, 1, Genre.MEN, Type.SUIT,"","");
    }

    private Item DummyItem2() {
        return new Item("name2", "desc2", Brand.ADIDAS, 1, 1, Genre.MEN, Type.SOCKS,"","");
    }


    @Test
    public void add_new_item_test() {
        //GIVEN
        when(repository.save(DummyItem())).thenReturn(DummyItem());
        //WHEN
        Item item = service.addItem(DummyItem());
        //THEN
        assertEquals(DummyItem(), item);
        verify(repository, times(1)).save(DummyItem());
    }

    @Test
    public void test_return_item_by_id() {
        //GIVEN
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(DummyItem()));
        //WHEN
        Optional<Item> itemId = service.searchItemById(1L);
        //THEN
        assertEquals("name1", itemId.get().getName());
        assertEquals("desc1", itemId.get().getDescription());
        assertEquals(DummyItem(), itemId.get());
    }

    @Test
    public void test_list_all_elements() {
        //GIVEN
        List<Item> expected = new ArrayList<>();
        expected.add(DummyItem());
        expected.add(DummyItem2());
        when(repository.findAll()).thenReturn(expected);
        //WHEN
        List<Item> result = service.listItems();
        //THEN
        assertEquals(2, result.size());
        assertThat(result).isEqualTo(expected);
        verify(repository, times(1)).findAll();

    }

    @Test
    public void list_same_brand_items_test() {
        //GIVEN
        when(repository.findByBrand("brand1")).thenReturn(Arrays.asList(DummyItem()));
        //WHEN
        List<Item> brands = service.searchItemByBrand("brand1");
        //THEN
        assertEquals(Arrays.asList(DummyItem()), brands);
    }

    @Test
    public void list_same_price_items_test() {
        //GIVEN
        when(repository.findByPriceLessThanEqual(1)).thenReturn(Arrays.asList(DummyItem(), DummyItem2()));
        //WHEN
        List<Item> items = service.searchItemByPrice(1);
        //THEN
        assertEquals(Arrays.asList(DummyItem(), DummyItem2()), items);
    }


    @Test
    public void test_update_an_elements() {
        //GIVEN
        doAnswer(invocation -> {
            Item item = invocation.getArgument(0);
            assertThat(item).isEqualTo(DummyItem2());
            return null;
        }).when(repository).save(DummyItem2());
        //WHEN
        service.changeItem(DummyItem2());
        //THEN
        verify(repository, times(1)).save(DummyItem2());
    }


//    @Test
//    public void test_delete_an_element() {
//        //GIVEN
//        doAnswer(invocation -> {
//            Item item = invocation.getArgument(0);
//            assertThat(item).isEqualTo(DummyItem());
//            return null;
//        }).when(repository).delete(DummyItem());
//        //WHEN
//        service.deleteItem(DummyItem());
//        //THEN
//        verify(repository, times(1)).delete(DummyItem());
//    }
}

