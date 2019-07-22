package dao;

import domain.usermodel.User;
import domain.usermodel.UserType;
import org.junit.Test;


import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.*;

public class InMemoryUserDAOTest {

    private InMemoryUserDAO dao = new InMemoryUserDAO();
    @Test
    public void returnAll() {
        //GIVEN
            User user1 = new User("Nagy","Bela","a@mail",
                    "Bp","123", UserType.REGISTERED);
            User user2 = new User("Nagy2","Bela2","a@mail2",
                    "Bp2","1232", UserType.REGISTERED);
            dao.create(user1);
            dao.create(user2);
        //WHEN
        List<User> all = dao.getAll();
        //THEN
        assertThat(all).containsExactly(user1, user2);
    }
    @Test
    public void create_method_test(){
        User user1 = new User("Nagy","Bela","a@mail",
                "Bp","123", UserType.REGISTERED);
        //
        User userAfterAdd = dao.create(user1);
        //
        assertThat(userAfterAdd).isEqualTo(user1);
    }


    @Test
    public void list_users_by_type_test() {
        //
        User user1 = new User("Nagy","Bela","a@mail",
                "Bp","123", UserType.REGISTERED);
        User user2 = new User("Nagy2","Bela2","a@mail2",
                "Bp2","1232", UserType.REGISTERED);
        dao.create(user1);
        dao.create(user2);
        int id = 1;
        //
        User userWithTheGivenId = dao.findUserById(id) ;
        //
        assertThat(userWithTheGivenId).isEqualTo(user2);
    }

    @Test
    public void list_same_type_elements_test(){
        //
        User user1 = new User("Nagy","Bela","a@mail",
                "Bp","123", UserType.REGISTERED);
        User user2 = new User("Nagy2","Bela2","a@mail2",
                "Bp","123", UserType.REGISTERED);
        User user3 = new User("Nagy3","Bela3","a@mail3",
                "Bp","123", UserType.ADMIN);
        dao.create(user1);
        dao.create(user2);
        dao.create(user3);
        //
        List<User> expected = Arrays.asList(user1,user2);
        List<User> actual = dao.listUsersByType(UserType.REGISTERED);
        //
        assertArrayEquals(expected.toArray(),actual.toArray());
    }
    @Test
    public void test_delete_an_element(){
        //
        User user1 = new User("Nagy","Bela","a@mail",
                "Bp","123", UserType.REGISTERED);
        User user2 = new User("Nagy2","Bela2","a@mail2",
                "Bp2","1232", UserType.ADMIN);
        User user3 = new User("Nagy3","Bela3","a@mail3",
                "Bp3","1233", UserType.GUEST);

        dao.create(user1);
        dao.create(user2);
        dao.create(user3);
        //
        dao.delete(user1);
        List<User> expected = Arrays.asList(user2,user3);
        List<User> actual = dao.getAll();
        //
        assertArrayEquals(expected.toArray(),actual.toArray());



    }
    @Test
    public void test_update_an_elements(){
        //
        User user1 = new User("Nagy","Bela","a@mail",
                "Bp","123", UserType.REGISTERED);
        User user2 = new User("Nagy","Bela","a@mail2",
                "Bp2","1234", UserType.ADMIN);
        //
        dao.update(user1,user2);
        //
        assertThat(user1).isEqualTo(user2);
    }



}