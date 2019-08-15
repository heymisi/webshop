package kmihaly.mywebshop.repository;

import kmihaly.mywebshop.domain.model.item.SelectedItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectedItemRepository extends JpaRepository<SelectedItem,Long> {
}
