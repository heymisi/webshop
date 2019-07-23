package kmihaly.mywebshop.service;

import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import org.junit.Assert;
import org.junit.Test;


import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertArrayEquals;

public class DAOUserServiceTest {

    private DAOUserService service = new DAOUserService();

    User user1 = new User("nickn1","Nagy","Bela","a@mail",
            "Bp","123", UserType.REGISTERED);
    User user2 = new User("nickn2","Nagy2","Bela2","a@mail2",
            "Bp2","1232", UserType.REGISTERED);


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
        service.deleteUser(1);

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
        service.updateUser(userForUp1, userForUp2);
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


}