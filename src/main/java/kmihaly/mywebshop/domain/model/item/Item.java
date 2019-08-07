package kmihaly.mywebshop.domain.model.item;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Az Item osztály a terméket reprezentálja
 * rendelkezik egyedi azonosítóval a megkülönböztehetőség végett
 * tároljuk a hozzátartozó tulajdonságokat, valamint azt hogy rendelkezésre áll e jelenleg
 */
@Data
@Entity
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String name;
    private String description;
    private String brand;
    private int price;
    private int rate;
    private int availableQuantity;
    @Enumerated
    private SizeType size;
    @Enumerated
    private GenreType genre;

    public Item() {}

    public Item(String name, String description, String brand, int price, int availableQuantity,GenreType genre) {
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.genre = genre;
    }

}
