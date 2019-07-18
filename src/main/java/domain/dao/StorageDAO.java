package domain.dao;

import domain.itemmodel.Storage;
import domain.usermodel.User;

import java.util.Collection;

public interface StorageDAO {
    /**
     * új kosár létrehozására szolgáló metódus
     * @param storage a kapott kosárt hozza létre
     */
    void createStorage(Storage storage);

    /**
     * kosár modósítására szolgáló metódus
     * @param storage a kapott kosár modosítja
     */
    void updateStorage(Storage storage);

    /**
     * kosár törlésére szolgáló metódus
     * @param storage a kapott kosárt törli
     */
    void deleteStorage(Storage storage);

    /**
     * a kosár beolvasására szolgáló metódus
     * @return az összes kosárt tartalmazó listájával tér vissza
     */
    Collection<User> readStorage();


}
