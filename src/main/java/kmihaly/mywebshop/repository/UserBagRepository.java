package kmihaly.mywebshop.repository;

import kmihaly.mywebshop.domain.model.item.SelectedItem;
import kmihaly.mywebshop.domain.model.item.UserBag;
import kmihaly.mywebshop.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBagRepository extends JpaRepository<UserBag,Long> {

    UserBag findByUser(User user);

}
