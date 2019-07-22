package kmihaly.mywebshop.service;

import kmihaly.mywebshop.dao.InMemoryUserDAO;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;

import java.util.List;

public class DAOUserService implements UserService {

    InMemoryUserDAO dao = new InMemoryUserDAO();

    @Override
    public List<User> listUsers() {
        return dao.getAll();
    }

    @Override
    public void deleteUser(int id) {
        dao.delete(id);
    }

    @Override
    public void updateUser(User user, User newUser) {
        dao.update(user, newUser);
    }

    @Override
    public User signIn(String userName, String password) {
        User user = dao.findUserByUserName(userName);
        if (user.getPassword() == password) {
            return user;
        }
        return null;
    }

    @Override
    public void register(String userName, String firstName, String lastName, String email, String address, String password) {
        dao.create(new User(userName, firstName, lastName, email, address, password, UserType.REGISTERED));
    }
}
