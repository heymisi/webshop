package kmihaly.mywebshop.service;

import kmihaly.mywebshop.domain.model.user.User;

import java.util.List;

public interface UserService {
    /**
     * kilistázza a regisztrált felasználókat
     *
     * @return visszadja a regisztrált felhasználók egy listáját
     */
    List<User> listUsers();
     /**
     *
     */

    User createUser(User user);
    /**
     * felhasználó törlése a rendszerből
     *
     * @param id a felhasználó aki törölve lesz
     */

    void deleteUser(int id);

    /**
     * metódus amely megváltoztatja egy felhasználó tulajdonságait
     *
     * @param user    a felhasználó akinek az adata változni fog
     * @param newUser a felhasználó adatai amiké változni fog
     */
    void updateUser(User user, User newUser);

    /**
     * metódus amely bejelentezésre szolgál
     *
     * @param userName a felhasználónév amit megkell adni a bejelentkezéshez
     * @param password a jelszó amit meg kell adni a bejelentkezéshez
     * @return visszaadja a bejelentkezett felhasználót
     */
    User signIn(String userName, String password);

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
