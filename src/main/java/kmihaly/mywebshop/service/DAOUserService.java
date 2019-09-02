package kmihaly.mywebshop.service;

import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import kmihaly.mywebshop.repository.UserRepository;
import kmihaly.mywebshop.security.RandomString;

import javax.jws.soap.SOAPBinding;
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
    public void register(String userName, String firstName, String lastName, String email, String address,String birthDate, String password) {
        repository.save(new User(userName, firstName, lastName, email, address,birthDate, password, UserType.USER));
    }

    @Override
    public boolean isUserNameUsed(String username) {
        if (repository.findByUserName(username).isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isPasswordsEquals(String psw1, String psw2) {
        if (psw1.equals(psw2)) {
            return true;
        }
        return false;
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