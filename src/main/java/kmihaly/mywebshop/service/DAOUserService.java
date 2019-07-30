package kmihaly.mywebshop.service;

import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import kmihaly.mywebshop.repository.UserRepository;

import static junit.framework.TestCase.assertNotNull;

import java.util.List;
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
        return repository.findByUserName(name);
    }

    @Override
    public List<User> findUserByType(UserType type) {
        return repository.findByUserType(type);
    }


    @Override
    public void deleteUser(User user) {
        assertNotNull(repository.findById(user.getId()));
        repository.delete(user);
    }

    @Override
    public void updateUser(User newUser) {
        assertNotNull(repository.findById(newUser.getId()));
        repository.save(newUser);
    }

    @Override
    public User signIn(String userName, String password) {
        User user = repository.findByUserName(userName);
        assertNotNull(user);
        if (user.getPassword() == password) {
            return user;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void register(String userName, String firstName, String lastName, String email, String address, String password) {
        repository.save(new User(userName, firstName, lastName, email, address, password, UserType.REGISTERED));
    }
}