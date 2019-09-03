package kmihaly.mywebshop.domain.model.user;

/**
 * Az UserType a különböző típusú felhasználó típusokat tárolja
 * a Guest joga annak van aki nem jelentkezik be
 * Registered az olyan felhasználó aki bejelentkezett felhasználójába, de nem admin
 * Admin akinek előre megadott speciális jogai vannak
 */
public enum UserType {
    GUEST("Guest"),
    USER("User"),
    ADMIN("Admin");

    private String name;

    UserType(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
