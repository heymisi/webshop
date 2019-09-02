package kmihaly.mywebshop.domain.model.item;

public enum Genre {
    MEN("Men"),WOMEN("Women");
    private String name;

    Genre(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
