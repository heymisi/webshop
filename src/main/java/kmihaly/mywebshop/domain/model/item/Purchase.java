package kmihaly.mywebshop.domain.model.item;

import kmihaly.mywebshop.domain.model.user.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A Purchase osztály egy vásárlásról tárol információkat
 * ezeknek eltárol egy azonosítót, egy dátumot, valamint a usert aki végrehajtja
 * az useren keresztül eltárolodnak a megvásárolt termékek is
 */
@Data
@Entity
public class Purchase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<SelectedItem> items;

    private int itemsPrice;

    @Temporal(TemporalType.DATE)
    private Date date;

    protected Purchase() {}

    public Purchase(User user, Date date) {
        this.user = user;
        this.date = date;
        items = new ArrayList<>();
    }

}
