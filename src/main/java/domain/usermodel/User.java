package domain.usermodel;

import domain.itemmodel.Storage;

import java.util.Objects;

/**
 * Az User az oldalt használó felhasználót reprezentálja
 * számos személyes tulajdonsággal rendelkeznek
 * elvan tárolva a hozzá tartozó joggosultsági szint
 * továbbá minden felhasználóhoz tartozik egy kosár a kiválsztott termékeivel
 */
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String password;
    private UserType userType;
    private Storage storage;

    public User(){};

    public User(String firstName, String lastName, String email, String address, String password, UserType userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.password = password;
        this.userType = userType;
        storage = new Storage();
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(address, user.address) &&
                Objects.equals(password, user.password) &&
                userType == user.userType &&
                Objects.equals(storage, user.storage);
    }

    @Override
    public int hashCode() {
        return Objects.hash( firstName, lastName, email, address, password, userType, storage);
    }

    @Override
    public String toString() {
        return "User{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                ", storage=" + storage +
                '}';
    }
}
