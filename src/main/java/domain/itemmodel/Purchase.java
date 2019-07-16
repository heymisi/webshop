package domain.itemmodel;

import domain.usermodel.User;
import java.util.Date;

public class Purchase {

    private Bucket bucket;
    private User user;
    private Date date;

    public Bucket getBucket() {
        return bucket;
    }

    public void setBucket(Bucket bucket) {
        this.bucket = bucket;
    }

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

}
