package domain.usermodel;

import domain.itemmodel.Storage;

/**
 * Az User az oldalt használó felhasználót reprezentálja
 * számos személyes tulajdonsággal rendelkeznek
 * elvan tárolva a hozzá tartozó joggosultsági szint
 * továbbá minden felhasználóhoz tartozik egy kosár a kiválsztott termékeivel
 *
 */
public class User {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String password;
    private UserType userType;
    private Storage storage;


    public User(String userName, String firstName, String lastName, String email, String address, String password, UserType userType, Storage storage) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.password = password;
        this.userType = userType;
        this.storage = storage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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
}
