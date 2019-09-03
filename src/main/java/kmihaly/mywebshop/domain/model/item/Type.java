package kmihaly.mywebshop.domain.model.item;

public enum Type {
    JEAN("Jean"),TROUSER("Trouser"),SHIRT("Shirt"),SHORTS("Shorts"),SUIT("Suit"),SOCKS("Socks");

    private String name;
    Type(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
