package service;

import dao.UserDAO;
import domain.usermodel.User;

import java.util.Collection;

public class DAOUserService implements UserService {

    @Override
    public Collection<User> listUsers() {
        return null;
    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public void updateUser(User user, User newUser) {

    }

    @Override
    public User signIn(String userName, String password) {
        return null;
    }

    @Override
    public User register(String userName, String firstName, String lastName, String email, String address, String password) {
        return null;
    }
}
