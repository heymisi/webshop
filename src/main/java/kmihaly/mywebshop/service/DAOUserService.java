package kmihaly.mywebshop.service;

import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import kmihaly.mywebshop.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DAOUserService implements UserService {

    private final UserRepository repository;

    public DAOUserService(UserRepository userRepository) {
        repository = userRepository;
    }

    @Override
    public List<User> listUsers() {
        return repository.findAll();
    }

    @Override
    public Optional<User> findUserById(long id) {
        return repository.findById(id);
    }

    @Override
    public User findUserByName(String name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("hibás bemenet!");
        }
        return repository.findByUserName(name);
    }

    @Override
    public List<User> findUserByType(UserType type) {
        return repository.findByUserType(type);
    }


    @Override
    public void deleteUser(User user) {
        if (Objects.isNull(user) || !(repository.findById(user.getId()).isPresent())) {
            throw new IllegalArgumentException("hibás bemenet!");
        }
        repository.delete(user);
    }

    @Override
    public void updateUser(User newUser) {
        if (Objects.isNull(newUser) || !(repository.findById(newUser.getId()).isPresent())) {
            throw new IllegalArgumentException("hibás bemenet!");
        }
        repository.save(newUser);
    }

    @Override
    public boolean signIn(String userName, String password) {
        User user = repository.findByUserName(userName);
        if (user.equals(null)) {
            return false;
        }
        if (user.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void register(String userName, String firstName, String lastName, String email, String address, String password) {

        repository.save(new User(userName, firstName, lastName, email, address, password, UserType.REGISTERED));
    }
}