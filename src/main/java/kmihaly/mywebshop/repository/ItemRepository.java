package kmihaly.mywebshop.repository;

import kmihaly.mywebshop.domain.model.item.Genre;
import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.domain.model.item.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {

    List<Item> findByBrand(String brand);

    List<Item> findByPriceLessThanEqual(int price);

    List<Item> findByGenre(Genre genre);

    List<Item> findByGenreAndBrand(Genre genre, String brand);

    List<Item> findByType(Type type);

    List<Item> findByOrderByPriceAsc();


}
