package domain.dao;

import domain.usermodel.User;
import domain.usermodel.UserType;

import java.util.Collection;

public interface UserDAO {
    /**
     * új felhasználó létrehozására szolgáló metódus
     * @param user a kapott felhasználót hozza létre
     */
    void createUser(User user);

    /**
     * felhasználó modósítására szolgáló metódus
     * @param user a kapott felhasználót modosítja
     */
    void updateUSer(User user);

    /**
     * felhasználó törlésére szolgáló metódus
     * @param user a kapott felhasználót törli
     */
    void deleteUSer(User user);

    /**
     * a felhasználók beolvasására szolgáló metótus
     * @return az összes felhasználot tartalmazó listájával tér vissza
     */
    Collection<User> readUsers();

    /**
     * a felhasználó típus szerinti listázása
     * @param type a típus amely szerint történik a listázás
     * @return visszatér a kapott felhasználók listájával
     */
    Collection<User> readUsersByType(UserType type);


}
