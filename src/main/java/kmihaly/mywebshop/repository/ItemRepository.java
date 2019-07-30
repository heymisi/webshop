package kmihaly.mywebshop.repository;

import kmihaly.mywebshop.domain.model.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {

    List<Item> findByBrand (String brand);

    List<Item> findByPrice(int price);

}
