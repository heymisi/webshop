package kmihaly.mywebshop.service;

import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import kmihaly.mywebshop.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class DAOUserServiceTest {

    @InjectMocks
    private DAOUserService service;

    @Mock
    private UserRepository repository;

    private User Dummy(){return new User("nickn1","Nagy","Bela","a@mail",
            "Bp","123", UserType.GUEST);}

    private User Dummy2(){return new User("nickn1","Nagy","Bela","a@mail",
            "Bp","123", UserType.REGISTERED);}





    @Test
    public void test_return_user_by_id() {
        //
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(Dummy()));
        //
        Optional<User> userById = service.findUserById(1L);
        //
        assertEquals(Dummy(), userById.get());
    }

    @Test
    public void test_list_all_elements() {

        //
        List<User> expected = new ArrayList<>();
        expected.add(Dummy());
        expected.add(Dummy2());
        when(repository.findAll()).thenReturn(expected);
        //
        List<User> result = service.listUsers();
        assertEquals(2, result.size());
        assertThat(result).isEqualTo(expected);
        verify(repository, times(1)).findAll();

    }

    @Test
    public void list_same_type_users_test() {
        //
        when(repository.findByUserType(UserType.GUEST)).thenReturn(Arrays.asList(Dummy()));

        List<User> userType = service.findUserByType(UserType.GUEST);

        assertEquals(Arrays.asList(Dummy()), userType);

    }

    @Test
    public void list_same_username_user_test() {
        //
       // when(repository.findByUserName("name1")).thenReturn(Dummy());

        User searchName = service.findUserByName("name1");

        assertEquals(Dummy(), searchName);

    }
    /*@Test
    public void add_new_item_test() {
        when(repository.save(Dummy())).thenReturn(Dummy());
        Item item = service.register(());

        assertEquals(DummyItem(),item);
        verify(repository, times(1)).save(DummyItem());
    }





    @Test
    public void list_all_user_test() {
        service.createUser(user1);
        service.createUser(user2);
        //
        List<User> actual = service.listUsers();
        List<User> expected = Arrays.asList(user1,user2);
        //
        Assert.assertArrayEquals(expected.toArray(),actual.toArray());
    }

    @Test
    public void delete_user_test() {
        service.createUser(user1);
        service.createUser(user2);
        //
        service.deleteUser(user1);

        List<User> expected = Arrays.asList(user2);
        List<User> actual = service.listUsers();
        //
        assertArrayEquals(expected.toArray(),actual.toArray());

    }

    @Test
    public void update_user_test(){
        User userForUp1 = new User("nickn1", "Nagy", "Bela", "a@mail",
                "Bp", "123", UserType.REGISTERED);
        User userForUp2= new User("nickn1", "Nagy", "Bela", "a@mail2",
                "Bp2", "1232", UserType.REGISTERED);

        //
        service.updateUser( userForUp2);
        //
        assertThat(userForUp2).isEqualToComparingFieldByField(userForUp2);

    }
    @Test public void sign_in_a_user_test(){
        service.createUser(user1);

        User actual = service.signIn("nickn1","123");
        assertThat(actual).isEqualTo(user1);

        User passwordTest = service.signIn("nickn1","1234");
        assertThat(passwordTest).isNull();
    }

*/

}
