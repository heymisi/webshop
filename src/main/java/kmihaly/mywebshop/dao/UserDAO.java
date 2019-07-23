package kmihaly.mywebshop.dao;

import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;

import java.util.List;

public interface UserDAO extends GenericDAO<User>{

    List<User> listUsersByType(UserType type);

    User findUserById(int id);

    User findUserByUserName(String userName);


}