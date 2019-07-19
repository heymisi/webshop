package dao;

import domain.usermodel.User;
import domain.usermodel.UserType;

import java.util.*;

public class InMemoryUserDAO extends GenericDAO<User> implements UserDAO {

    private int id = 0;
    private Map<Integer,User> tables = new HashMap<>();

    @Override
    User add(User user) {
        int Id = id++;
        user.setId(Id);
        tables.put(id, user);
        return user;
    }

    @Override
    void create(User user) {
        add(user);
    }

    @Override
    void update(User user, User user2) {
    User oldUser = findUserById(user.getId());
    oldUser = user;

    }

    @Override
    void delete(User user) {
        tables.remove(user.getId());
    }

    @Override
    List<User> getAll() {
        return new ArrayList<>(tables.values());
    }

    @Override
    public List<User> listUsersByType(UserType type) {
        List<User> results = new ArrayList<>();
        for(User user : tables.values()){
            if(user.getUserType().equals(type)){
                results.add(user);
            }
        }
        return results;
    }

    @Override
    public User findUserById(long id) {
        User result = new User();
        for (User user : tables.values()) {
            if (user.getId() == id) {
                result = user;
            }
        }
        return result;
    }
}
