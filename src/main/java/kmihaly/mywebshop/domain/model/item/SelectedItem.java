package kmihaly.mywebshop.domain.model.item;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Az SelectedItem az Item osztály egy kiterjesztése
 * annyiban egészíti ki azt, hogy a rendelésnél kiválasztott mennyiségét is eltárolja a termékkel együtt
 */
@Data
@Entity
public class SelectedItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Item item;

    private int quantity;


    protected SelectedItem() {
    }

    public SelectedItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

}
