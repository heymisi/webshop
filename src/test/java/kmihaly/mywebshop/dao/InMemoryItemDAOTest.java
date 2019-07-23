package kmihaly.mywebshop.dao;

import kmihaly.mywebshop.domain.model.item.Item;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.*;

public class InMemoryItemDAOTest {
    private InMemoryItemDAO dao = new InMemoryItemDAO();
    Item item1 = new Item("name1", "decr1", "brand1", 1, 1);
    Item item2 = new Item("name2", "decr2", "brand1", 1, 1);
    Item item3 = new Item("name3", "decr3", "brand2", 2, 1);

    @Test
    public void returnAll() {
        //GIVEN
        dao.create(item1);
        dao.create(item2);
        //WHEN
        Collection<Item> all = dao.getAll();
        //THEN
        assertThat(all).containsExactly(item1, item2);
    }

    @Test
    public void create_method_test() {
        //
        Item itemAfterAdd = dao.create(item1);
        //
        assertThat(itemAfterAdd).isEqualTo(item1);
    }


    @Test
    public void test_return_item_by_id() {
        //
        dao.create(item1);
        dao.create(item2);
        //
        int id = 2;
        Item itemWithTheGivenId = dao.selectItemById(id);
        //
        assertThat(itemWithTheGivenId).isEqualTo(item2);
    }

    @Test
    public void list_same_brand_items_test() {
        //
        dao.create(item1);
        dao.create(item2);
        dao.create(item3);
        //
        Collection<Item> expected = Arrays.asList(item1, item2);
        Collection<Item> actual = dao.ListItemsByBrand("brand1");
        //
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void list_same_price_items_test() {
        //
        dao.create(item1);
        dao.create(item2);
        dao.create(item3);
        //
        Collection<Item> expected = Arrays.asList(item1, item2);
        Collection<Item> actual = dao.ListItemsByPrice(1);
        //
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void test_delete_an_element() {
        //
        dao.create(item1);
        dao.create(item2);
        dao.create(item3);
        //
        dao.delete(2);
        List<Item> expected = Arrays.asList(item1, item3);
        Collection<Item> actual = dao.getAll();
        //
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void test_update_an_elements() {
        //
        Item itemForUpd1 = new Item("name1", "decr1", "brand1", 1, 1);
        Item itemForUpd2 = new Item("name2", "decr2", "brand1", 1, 1);
        //
        dao.update(itemForUpd1, itemForUpd2);
        //
        assertThat(itemForUpd1).isEqualToComparingFieldByField(itemForUpd2);
    }

}