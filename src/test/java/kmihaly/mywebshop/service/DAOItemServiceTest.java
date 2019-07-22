package kmihaly.mywebshop.service;

import kmihaly.mywebshop.dao.InMemoryItemDAO;
import kmihaly.mywebshop.domain.model.item.Item;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertArrayEquals;

public class DAOItemServiceTest {
    private DAOItemService service = new DAOItemService();
    Item item1 = new Item("name1", "decr1", "brand1", 1, 1);
    Item item2 = new Item("name2", "decr2", "brand1", 1, 1);
    Item item3 = new Item("name3", "decr3", "brand2", 2, 1);

    @Test
    public void test_delete_an_element() {
        //
        service.addItem(item1);
        service.addItem(item2);
        service.addItem(item3);
        //
        service.deleteItem(2);
        List<Item> expected = Arrays.asList(item1, item3);
        Collection<Item> actual = service.listItems();
        //
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void test_update_an_elements() {
        //
        Item itemForUpd1 = new Item("name1", "decr1", "brand1", 1, 1);
        Item itemForUpd2 = new Item("name2", "decr2", "brand1", 1, 1);
        //
        service.changeItem(itemForUpd1, itemForUpd2);
        //
        assertThat(itemForUpd1).isEqualToComparingFieldByField(itemForUpd2);
    }

    @Test
    public void returnAll() {
        //GIVEN
        service.addItem(item1);
        service.addItem(item2);
        //WHEN
        List<Item> all = service.listItems();
        //THEN
        assertThat(all).containsExactly(item1, item2);
    }

    @Test
    public void create_method_test() {
        //
        Item itemAfterAdd = service.addItem(item1);
        //
        assertThat(itemAfterAdd).isEqualTo(item1);
    }

    @Test
    public void test_return_item_by_id() {
        //
        service.addItem(item1);
        service.addItem(item2);
        //
        int id = 1;
        Item itemWithTheGivenId = service.searchItemById(id);
        //
        assertThat(itemWithTheGivenId).isEqualTo(item1);
    }

    @Test
    public void list_same_price_items_test() {
        //
        service.addItem(item1);
        service.addItem(item2);
        service.addItem(item3);
        //
        Collection<Item> expected = Arrays.asList(item1, item2);
        Collection<Item> actual = service.searchItemByPrice(1);
        //
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void list_same_brand_items_test() {
        //
        service.addItem(item1);
        service.addItem(item2);
        service.addItem(item3);
        //
        Collection<Item> expected = Arrays.asList(item1, item2);
        Collection<Item> actual = service.searchItemByBrand("brand1");
        //
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

}