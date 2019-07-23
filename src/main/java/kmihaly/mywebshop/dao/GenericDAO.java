package kmihaly.mywebshop.dao;

import java.util.List;

public interface  GenericDAO<T> {


    /**
     * létrehozására szolgáló metódus
     *
     * @param t az ojbektum amelyet  létrehozunk
     */

    T create(T t);

    /**
     * modósítására szolgáló metódus
     *
     * @param t az objektum amelyet modosítunk
     */
    void update(T t, T n) ;

    /**
     * törlésére szolgáló metódus
     *
     * @param id az objektum amelyet törlöl
     */
    void delete(int id);

    /**
     * összes elem kilistázására szolgáló metótud
     *
     * @return visszaadja a kapott elemeket
     */
    List<T> getAll() ;

}
