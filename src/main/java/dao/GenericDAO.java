package dao;

import java.util.Collection;

public abstract class GenericDAO<T> {


    /**
     * létrehozására szolgáló metódus
     * @param t az ojbektum amelyet  létrehozunk
     */

    T create(T t){return null;};

    /**
     * modósítására szolgáló metódus
     * @param t az objektum amelyet modosítunk
     */
    void update(T t, T n){};

    /**
     * törlésére szolgáló metódus
     * @param t az objektum amelyet törlöl
     */
    void delete(T t){};

    /**
     * összes elem kilistázására szolgáló metótud
     * @return visszaadja a kapott elemeket
     */
    Collection<T> getAll() {return null;} ;

}
