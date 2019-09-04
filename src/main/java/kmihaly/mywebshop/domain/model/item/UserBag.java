package kmihaly.mywebshop.domain.model.item;

import kmihaly.mywebshop.domain.model.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class UserBag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    User user;

    @OneToMany(fetch = FetchType.EAGER)
    List<SelectedItem> items;

    protected UserBag() {
    }

    public UserBag(User user) {
        this.user = user;
        items = new ArrayList<>();
    }

    public void addItems(SelectedItem item) {
        items.add(item);
    }
}
