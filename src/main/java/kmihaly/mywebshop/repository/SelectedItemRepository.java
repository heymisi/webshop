package kmihaly.mywebshop.repository;

import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.domain.model.item.SelectedItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SelectedItemRepository extends JpaRepository<SelectedItem,Long> {
}
