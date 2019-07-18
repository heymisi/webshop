package domain.dao;

import domain.usermodel.User;
import domain.usermodel.UserType;

import java.util.Collection;

public interface UserDAO {

    void createUser(User user);

    void updateUSer(User user);

    void deleteUSer(User user);

    Collection<User> readUsers();

    Collection<User> readUsersByType(UserType type);


}
