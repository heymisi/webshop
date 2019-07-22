package kmihaly.mywebshop.dao;

import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;

import java.util.*;

public class InMemoryUserDAO extends GenericDAO<User> implements UserDAO {

    private int id = 0;
    private Map<Integer, User> tables = new HashMap<>();

    @Override
    public User create(User user) {
        int Id = id++;
        user.setId(Id);
        tables.put(id, user);
        return user;
    }


    @Override
    public void update(User user, User user2) {
        user.setAddress(user2.getAddress());
        user.setEmail(user2.getEmail());
        user.setPassword(user2.getPassword());
        user.setUserType(user2.getUserType());
    }

    @Override
    public void delete(int id) {
        tables.remove(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(tables.values());
    }

    @Override
    public List<User> listUsersByType(UserType type) {
        List<User> results = new ArrayList<>();
        for (User user : tables.values()) {
            if (user.getUserType().equals(type)) {
                results.add(user);
            }
        }
        return results;
    }

    @Override
    public User findUserById(int id) {
        User result = new User();
        for (User user : tables.values()) {
            if (user.getId() == id) {
                result = user;
            }
        }
        return result;
    }

    @Override
    public User findUserByUserName(String userName) {
        User result = new User();
        for (User user : tables.values()) {
            if (user.getUserName() == userName) {
                result = user;
            }
        }
        return result;
    }
}
