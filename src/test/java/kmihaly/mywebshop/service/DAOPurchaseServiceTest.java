package kmihaly.mywebshop.service;

import kmihaly.mywebshop.dao.InMemoryPurchaseDAO;
import kmihaly.mywebshop.domain.model.item.Purchase;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertArrayEquals;

public class DAOPurchaseServiceTest {

    DAOPurchaseService service = new DAOPurchaseService();
    Purchase pur1 = new Purchase(new User("nickn1", "Nagy1", "Bela1", "a@mail1",
            "Bp1", "1231", UserType.REGISTERED), new Date(2015, 01, 12));
    Purchase pur2 = new Purchase(new User("nickn2", "Nagy2", "Bela2", "a@mail2",
            "Bp2", "1232", UserType.REGISTERED), new Date(2015, 01, 12));
    Purchase pur3 = new Purchase(new User("nickn2", "Nagy2", "Bela2", "a@mail2",
            "Bp2", "1232", UserType.REGISTERED), new Date(2015, 01, 15));

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


    }


}