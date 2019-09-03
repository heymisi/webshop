package kmihaly.mywebshop.domain.model.item;

public enum Brand {

    NIKE("Nike"),ADIDAS("Adidas"),CONVERSE("Converse"),ZARA("Zara");

    private String name;
    Brand(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
