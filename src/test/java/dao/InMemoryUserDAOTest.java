package dao;

import domain.usermodel.User;
import domain.usermodel.UserType;
import org.junit.Ignore;
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
            dao.add(user1);
            dao.add(user2);
        //WHEN
        List<User> all = dao.getAll();
        //THEN
        assertThat(all).containsExactly(user1, user2);
    }
    @Test
    public void add_method_test(){
        User user1 = new User("Nagy","Bela","a@mail",
                "Bp","123", UserType.REGISTERED);
        //
        User userAfterAdd = dao.add(user1);
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
        dao.add(user1);
        dao.add(user2);
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
        dao.add(user1);
        dao.add(user2);
        dao.add(user3);
        //
        List<User> expected = Arrays.asList(user1,user2);
        List<User> actual = dao.listUsersByType(UserType.REGISTERED);
        //
        assertArrayEquals(expected.toArray(),actual.toArray());
    }
    @Ignore
    @Test
    public void test_delete_an_element(){
        //
        User user1 = new User("Nagy","Bela","a@mail",
                "Bp","123", UserType.REGISTERED);
        User user2 = new User("Nagy2","Bela2","a@mail2",
                "Bp","123", UserType.REGISTERED);
        dao.add(user1);
        dao.add(user2);

        //
        dao.delete(user1);

        //
        assertThat(dao.getAll().size()).isEqualTo(0);


    }
    @Test
    public void test_update_an_elements(){
        User user1 = new User("Nagy","Bela","a@mail",
                "Bp","123", UserType.REGISTERED);
        User user2 = new User("Nagy2","Bela2","a@mail2",
                "Bp","123", UserType.REGISTERED);


        //
        dao.update(user2,user1);
        //
        assertThat(user2).isEqualTo(user1);
    }



}