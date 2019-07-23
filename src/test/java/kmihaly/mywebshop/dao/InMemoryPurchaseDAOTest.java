package kmihaly.mywebshop.dao;

import kmihaly.mywebshop.domain.model.item.Purchase;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.*;

public class InMemoryPurchaseDAOTest {

    private InMemoryPurchaseDAO dao = new InMemoryPurchaseDAO();
    Purchase pur1 = new Purchase(new User("nickn1", "Nagy1", "Bela1", "a@mail1",
            "Bp1", "1231", UserType.REGISTERED), new Date(2015, 01, 12));
    Purchase pur2 = new Purchase(new User("nickn2", "Nagy2", "Bela2", "a@mail2",
            "Bp2", "1232", UserType.REGISTERED), new Date(2015, 01, 12));
    Purchase pur3 = new Purchase(new User("nickn2", "Nagy2", "Bela2", "a@mail2",
            "Bp2", "1232", UserType.REGISTERED), new Date(2015, 01, 15));

    @Test
    public void returnAll() {
        //GIVEN

        dao.create(pur1);
        dao.create(pur2);
        //WHEN
        Collection<Purchase> all = dao.getAll();
        //THEN
        assertThat(all).containsExactly(pur1, pur2);
    }

    @Test
    public void create_method_test() {
        //
        Purchase purchaseAfterAdd = dao.create(pur1);
        //
        assertThat(purchaseAfterAdd).isEqualTo(pur1);
    }


    @Test
    public void list_purchases_by_user_test() {
        //
        dao.create(pur1);
        dao.create(pur2);
        User user = new User("nickn1", "Nagy1", "Bela1", "a@mail1",
                "Bp1", "1231", UserType.REGISTERED);
        //
        List<Purchase> expected = Arrays.asList(pur1);
        Collection<Purchase> actual = dao.readPurhcasesByUser(user);

        //
        assertArrayEquals(expected.toArray(), actual.toArray());
    }


    @Test
    public void test_delete_a_purchase() {
        //
        dao.create(pur1);
        dao.create(pur2);
        //
        dao.delete(2);
        List<Purchase> expected = Arrays.asList(pur1);
        Collection<Purchase> actual = dao.getAll();
        //
        assertArrayEquals(expected.toArray(), actual.toArray());


    }

    @Test
    public void test_update_a_purchase() {
        //
        Purchase pur1 = new Purchase(new User(), new Date(2012, 12, 1));
        Purchase pur2 = new Purchase(new User(), new Date(2013, 12, 1));

        //
        dao.update(pur1, pur2);
        //
        assertThat(pur1).isEqualToComparingFieldByField(pur2);
    }

    @Test
    public void list_purchases_by_date_test() {
        //
        dao.create(pur1);
        dao.create(pur2);
        dao.create(pur3);
        //
        List<Purchase> expected = Arrays.asList(pur1, pur2);
        Collection<Purchase> actual = dao.readPurhcasesByDate(new Date(2015, 01, 12));
        //
        assertArrayEquals(expected.toArray(), actual.toArray());
    }
    /*
    @Ignore
    @Test
    public void list_purchases_by_Item_test(){
        //

        Purchase pur1 = new Purchase(new User("nickn1","Nagy1","Bela1","a@mail1",
                "Bp1","1231", UserType.REGISTERED),new Date());
        Purchase pur2 = new Purchase(new User("nickn2","Nagy2","Bela2","a@mail2",
                "Bp2","1232", UserType.REGISTERED),new Date());
        dao.create(pur1);
        dao.create(pur2);
        //
        List<Purchase> expected = Arrays.asList(pur1);
        List<Purchase> actual = dao.readPurhcasesByItem(pur1);
        //
        assertArrayEquals(expected.toArray(),actual.toArray());
    }
    */
}