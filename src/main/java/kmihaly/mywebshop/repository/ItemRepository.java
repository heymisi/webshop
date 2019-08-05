package kmihaly.mywebshop.repository;

import kmihaly.mywebshop.domain.model.item.GenreType;
import kmihaly.mywebshop.domain.model.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {

    List<Item> findByBrand(String brand);

    List<Item> findByPriceLessThan(int price);

    List<Item> findByGenre(GenreType genre);

    List<Item> findByGenreAndBrand(GenreType genre, String brand);
   /*  @Query("SELECT * from Item WHERE ")
    List<Item> findItems (int size);*/
}
