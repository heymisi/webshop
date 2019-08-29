package kmihaly.mywebshop.domain.model.user;

import kmihaly.mywebshop.domain.model.item.SelectedItem;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Az User az oldalt használó felhasználót reprezentálja
 * számos személyes tulajdonsággal rendelkeznek
 * elvan tárolva a hozzá tartozó joggosultsági szint
 * továbbá minden felhasználóhoz tartozik egy kosár a kiválsztott termékeivel
 */
@Data
@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String password;
    private UserType userType;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<SelectedItem> selectedItems;


    public User() {}

    public User(String userName, String firstName, String lastName, String email, String address, String password, UserType userType) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.password = password;
        this.userType = userType;
        selectedItems = new ArrayList<>();
    }

    public void addItem(SelectedItem item){
        selectedItems.add(item);
    }
}
