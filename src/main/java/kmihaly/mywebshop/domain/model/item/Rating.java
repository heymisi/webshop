package kmihaly.mywebshop.domain.model.item;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private double value;
    private int counter;

    public Rating(){
        value = 0.0;
        counter = 0;
    }

}
