package domain.itemmodel;

import domain.usermodel.User;
import java.util.Date;

/**
 * A Purchase osztály egy vásárlásról tárol információkat
 * ezeknek eltárol egy azonosítót, egy dátumot, valamint a usert aki végrehajtja
 * az useren keresztül eltárolodnak a megvásárolt termékek is
 */
public class Purchase {

    private int orderId;
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

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

}
