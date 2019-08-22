package kmihaly.mywebshop.service;

import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;

import java.util.List;
import java.util.Optional;


public interface UserService {
    /**
     * kilistázza a regisztrált felasználókat
     *
     * @return visszadja a regisztrált felhasználók egy listáját
     */
    List<User> listUsers();

    /**
     * @return
     */
    Optional<User> findUserById(long id);

    User findUserByName(String name);

    List<User> findUserByType(UserType type);

     User findUserByEmail(String email);


        /**
         * felhasználó törlése a rendszerből
         *
         * @param id a felhasználó aki törölve lesz
         */

    void deleteUser(User user);

    /**
     * metódus amely megváltoztatja egy felhasználó tulajdonságait
     *
     * @param user    a felhasználó akinek az adata változni fog
     * @param newUser a felhasználó adatai amiké változni fog
     */
    void createUser(User newUser);

    /**
     * metódus amely bejelentezésre szolgál
     *
     * @param userName a felhasználónév amit megkell adni a bejelentkezéshez
     * @param password a jelszó amit meg kell adni a bejelentkezéshez
     * @return visszaadja a bejelentkezett felhasználót
     */
    boolean signIn(String userName, String password);

    /**
     * új felhasználó létrehozását végzi
     *
     * @param userName  felhasználó egyedi azonosító neve
     * @param firstName felhasználó vezetékneve
     * @param lastName  felhasználó kereszneve
     * @param email     felhasználó emailcíme
     * @param address   felhaszáló lakcíme
     * @param password  felhasználó jelszava
     * @return visszaadja a létrehozott felhasználót
     */

    void register(String userName, String firstName, String lastName, String email, String address, String password);
}