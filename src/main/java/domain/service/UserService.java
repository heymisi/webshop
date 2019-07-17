package domain.service;

import domain.usermodel.User;

import java.util.Collection;

public interface UserService {
    /**
     * kilistázza a regisztrált felasználókat
     * @return visszadja a regisztrált felhasználók egy listáját
     */
    Collection<User> listUsers();

    /**
     * felhasználó törlése a rendszerből
     * @param user a felhasználó aki törölve lesz
     */
    void deleteUser(User user);

    /**
     *metódus amely megváltoztatja egy felhasználó jelszavát
     * @param user a felhasználó akinek a jelszava változni fog
     * @param password az új jelszó
     */
    void updateUserPassword(User user, String password);

    /**
     * metódus amely bejelentezésre szolgál
     * @param userName a felhasználónév amit megkell adni a bejelentkezéshez
     * @param password a jelszó amit meg kell adni a bejelentkezéshez
     * @return visszaadja a bejelentkezett felhasználót
     */
    User signIn(String userName, String password);

    /**
     * új felhasználó létrehozását végzi
     * @param userName felhasználó egyedi azonosító neve
     * @param firstName felhasználó vezetékneve
     * @param lastName felhasználó kereszneve
     * @param email felhasználó emailcíme
     * @param address felhaszáló lakcíme
     * @param password felhasználó jelszava
     * @return visszaadja a létrehozott felhasználót
     */

    User register(String userName, String firstName, String lastName, String email, String address, String password);
}
