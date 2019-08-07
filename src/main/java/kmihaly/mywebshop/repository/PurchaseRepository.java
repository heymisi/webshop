package kmihaly.mywebshop.repository;

import kmihaly.mywebshop.domain.model.item.Purchase;
import kmihaly.mywebshop.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {

    User findByUser(User user);

    List<Purchase> findByDate(Date date);
}
