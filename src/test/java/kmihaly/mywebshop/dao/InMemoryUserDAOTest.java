package kmihaly.mywebshop.dao;

import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import org.junit.Test;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.*;

public class InMemoryUserDAOTest {

    private InMemoryUserDAO dao = new InMemoryUserDAO();
    User user1 = new User("nickn1", "Nagy", "Bela", "a@mail",
            "Bp", "123", UserType.USER);
    User user2 = new User("nickn2", "Nagy2", "Bela2", "a@mail2",
            "Bp2", "1232", UserType.USER);
    User user3 = new User("nickn3", "Nagy3", "Bela3", "a@mail3",
            "Bp", "123", UserType.ADMIN);

    @Test
    public void returnAll() {
        //GIVEN
        dao.create(user1);
        dao.create(user2);
        //WHEN
        Collection<User> all = dao.getAll();
        //THEN
        assertThat(all).containsExactly(user1, user2);
    }

    @Test
    public void create_method_test() {

        //
        User userAfterAdd = dao.create(user1);
        //
        assertThat(userAfterAdd).isEqualTo(user1);
    }


    @Test
    public void list_users_by_type_test() {
        //
        dao.create(user1);
        dao.create(user2);
        long id = 1L;
        //
        User userWithTheGivenId = dao.findUserById(id);
        //
        assertThat(userWithTheGivenId).isEqualTo(user2);
    }

    @Test
    public void list_same_type_users_test() {
        //
        dao.create(user1);
        dao.create(user2);
        dao.create(user3);
        //
        List<User> expected = Arrays.asList(user1, user2);
        List<User> actual = dao.listUsersByType(UserType.USER);
        //
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void test_delete_an_user() {
        //
        dao.create(user1);
        dao.create(user2);
        dao.create(user3);
        //
        dao.delete(1L);
        dao.delete(2L);
        List<User> expected = Arrays.asList(user3);
        Collection<User> actual = dao.getAll();
        //
        assertArrayEquals(expected.toArray(), actual.toArray());

    }

    @Test
    public void test_update_an_user() {
        //
        User user1 = new User("nickn1", "Nagy", "Bela", "a@mail",
                "Bp", "123", UserType.USER);
        User user2 = new User("nickn1", "Nagy", "Bela", "a@mail2",
                "Bp2", "1232", UserType.USER);
        //
        dao.update(user1, user2);
        //
        assertThat(user1).isEqualToComparingFieldByField(user2);
    }

    @Test
    public void search_user_by_username_test(){

        dao.create(user1);
        dao.create(user2);

        User userByName = dao.findUserByUserName("nickn2");
        assertThat(userByName).isEqualTo(user2);

    }

}