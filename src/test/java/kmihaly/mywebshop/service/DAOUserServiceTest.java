package kmihaly.mywebshop.service;

import kmihaly.mywebshop.dao.InMemoryUserDAO;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.*;

public class DAOUserServiceTest {
    @InjectMocks

    private DAOUserService service ;
    @Mock
    private InMemoryUserDAO dao;

    User user1 = new User("nickn1","Nagy","Bela","a@mail",
            "Bp","123", UserType.REGISTERED);
    User user2 = new User("nickn2","Nagy2","Bela2","a@mail2",
            "Bp2","1232", UserType.REGISTERED);
    User user3 = new User("nickn3","Nagy3","Bela3","a@mail3",
            "Bp","123", UserType.ADMIN);

    @Test
    public void list_all_user_test() {
        when(dao.getAll()).thenReturn(Arrays.asList(user1,user2));
        //

        //
        List<User> actual = service.listUsers();
        //
        verify(dao, times(1)).getAll();


    }
/*
    @Test
    public void delete_user_test() {
        dao.create(user1);
        dao.create(user2);
        dao.create(user3);
        //
        service.deleteUser(1);
        service.deleteUser(2);

        List<User> expected = Arrays.asList(user3);
        Collection<User> actual = service.listUsers();
        //
        assertArrayEquals(expected.toArray(),actual.toArray());


    }

 */
}