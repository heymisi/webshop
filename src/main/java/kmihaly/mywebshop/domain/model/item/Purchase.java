package kmihaly.mywebshop.domain.model.item;

import kmihaly.mywebshop.domain.model.user.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * A Purchase osztály egy vásárlásról tárol információkat
 * ezeknek eltárol egy azonosítót, egy dátumot, valamint a usert aki végrehajtja
 * az useren keresztül eltárolodnak a megvásárolt termékek is
 */
@Data
@Entity
@Table(name = "purchase")
public class Purchase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne()
    private User user;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "purchase_id")
    private Set<SelectedItem> items;

    private int itemsPrice;

    @Temporal(TemporalType.DATE)
    private Date date;

    protected Purchase() {}

    public Purchase(User user, Date date,int itemsPrice) {
        this.user = user;
        this.date = date;
        this.itemsPrice = itemsPrice;
        items = new HashSet<>();
    }

    public void addItem(SelectedItem item){
        items.add(item);
    }

}
