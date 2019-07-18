package domain.itemmodel;

import domain.usermodel.User;
import java.util.Date;
import java.util.Objects;

/**
 * A Purchase osztály egy vásárlásról tárol információkat
 * ezeknek eltárol egy azonosítót, egy dátumot, valamint a usert aki végrehajtja
 * az useren keresztül eltárolodnak a megvásárolt termékek is
 */
public class Purchase {

    private int id;
    private User user;
    private Date date;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", user=" + user +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return id == purchase.id &&
                Objects.equals(user, purchase.user) &&
                Objects.equals(date, purchase.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, date);
    }

}
