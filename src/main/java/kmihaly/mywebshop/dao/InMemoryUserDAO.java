package kmihaly.mywebshop.dao;

import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryUserDAO  implements UserDAO {

    private long id = 0;
    private Map<Long, User> tables = new HashMap<>();

    @Override
    public User create(User user) {
        long Id = id++;
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
    public void delete(long id) {
        tables.remove(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(tables.values());
    }

        @Override
        public List<User> listUsersByType(UserType type) {

        List<User> result = tables.values().stream()
                .filter(user -> user.getUserType().equals(type))
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public User findUserById(long id) {

        User result = tables.values().stream()
                .filter(user -> user.getId() == id)
                .findAny()
                .orElse(null);
        return result;
    }

    @Override
    public User findUserByUserName(String userName) {

        User result = tables.values().stream()
                .filter(user -> user.getUserName().equals(userName))
                .findAny()
                .orElse(null);
        return result;
    }
}
