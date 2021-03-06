package kmihaly.mywebshop.service;

import kmihaly.mywebshop.domain.model.item.UserBag;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import kmihaly.mywebshop.repository.UserBagRepository;
import kmihaly.mywebshop.repository.UserRepository;
import kmihaly.mywebshop.security.RandomString;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DAOUserService implements UserService {

    private final UserRepository repository;

    private final UserBagRepository userBagRepository;

    public DAOUserService(UserRepository userRepository, UserBagRepository userBagRepository) {
        repository = userRepository;
        this.userBagRepository = userBagRepository;
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
        return repository.findByUserName(name).orElse(null);
    }

    @Override
    public List<User> findUserByType(UserType type) {
        return repository.findByUserType(type);
    }

    @Override
    public User findUserByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }


    @Override
    public void deleteUser(User user) {
        if (Objects.isNull(user) || !(repository.findById(user.getId()).isPresent())) {
            throw new IllegalArgumentException("hibás bemenet!");
        }
        repository.delete(user);
    }

    @Override
    public void createUser(User newUser) {
        repository.save(newUser);
    }

    @Override
    public boolean signIn(String userName, String password) {
        User user = repository.findByUserName(userName).orElseThrow(() -> new IllegalArgumentException("nincs ilyen felhasználó"));
        return user.getPassword().equals(password);
    }

    @Override
    public void register(String userName, String firstName, String lastName, String email, String address, String birthDate, String password) {
        User user = new User(userName, firstName, lastName, email, address, birthDate, password, UserType.USER);
        repository.save(user);
        userBagRepository.save(new UserBag(user));
    }

    @Override
    public boolean isUserNameUsed(String username) {
        return repository.findByUserName(username).isPresent();
    }

    @Override
    public boolean isPasswordsEquals(String psw1, String psw2) {
        return psw1.equals(psw2);
    }

    @Override
    public String generateNewPassword(User user) {
        RandomString session = new RandomString();
        String newPassword = session.nextString();
        user.setPassword(newPassword);
        repository.save(user);
        return newPassword;
    }


}