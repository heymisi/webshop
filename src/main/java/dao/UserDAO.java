package dao;

import domain.usermodel.User;
import domain.usermodel.UserType;

import java.util.Collection;
import java.util.List;

public interface UserDAO {

    List<User> listUsersByType(UserType type);

    User findUserById(long id);



}