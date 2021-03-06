package kmihaly.mywebshop.domain.model.item;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
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
    private Long id;
    private String name;
    private String description;
    private int price;
    @OneToOne(cascade = CascadeType.ALL)
    private Rating rate;
    private int availableQuantity;
    @Enumerated
    private Brand brand;
    @Enumerated
    private Size size;
    @Enumerated
    private Genre genre;
    @Enumerated
    private Type type;
    private String smallImagePath;
    private String LargeImagePath;

    public Item() {}

    public Item(String name, String description,Brand brand, int price, int availableQuantity, Genre genre, Type type, String smallImagePath,String LargeImagePath) {
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.genre = genre;
        this.type = type;
        this.smallImagePath = smallImagePath;
        this.LargeImagePath = LargeImagePath;
        rate = new Rating();
    }

}
