package domain.service;

import domain.usermodel.User;

public interface UserService {
    void createNewUser(User user);
    void deleteUser(User user);
    void updateUser(User user);
    User signIn(String userName, String password);
    User register(String userName, String firstName, String lastName, String email, String address, String password);
}
