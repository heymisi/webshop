package kmihaly.mywebshop.configuration;


import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.domain.model.item.Purchase;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import kmihaly.mywebshop.repository.ItemRepository;
import kmihaly.mywebshop.repository.PurchaseRepository;
import kmihaly.mywebshop.repository.UserRepository;
import kmihaly.mywebshop.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Date;


@Configuration
public class ApplicationConfiguration {

    @Bean
    public ItemService itemService(ItemRepository itemRepository) {
        return new DAOItemService(itemRepository);
    }

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new DAOUserService(userRepository);
    }

    @Bean
    public PurchaseService purchaseService (PurchaseRepository purchaseRepository) { return new DAOPurchaseService(purchaseRepository); }

    @Bean
    public CommandLineRunner commandLineRunner(ItemRepository itemRepository, UserRepository userRepository) {
        return args -> {

            userRepository.save(new User("usern", "firsn", "lastn", "mail", "ad", "psw", UserType.REGISTERED));
            userRepository.save(new User("usern2", "firsn2", "lastn2", "mail2", "ad2", "psw2", UserType.REGISTERED));
            itemRepository.save(new Item("name", "desc", "a", 1, 1));
            itemRepository.save(new Item("nam2e", "2", "a2", 1, 1));

            Item item = new Item("nam2e", "2", "a2", 1, 1);
            Item item2 = new Item("nam3e", "3", "a2", 1, 1);
            Item item3 = new Item("nam4e", "4", "a2", 1, 1);
            Item item4 = new Item("nam5e", "2", "a2", 1, 1);
            Item item5 = new Item("nam6e", "1", "a2", 1, 1);
            item.setId(1L);
            itemService(itemRepository).changeItem(item);
            itemService(itemRepository).changeItem(item2);

            itemService(itemRepository).changeItem(item3);

            itemService(itemRepository).changeItem(item4);
            itemService(itemRepository).changeItem(item5);




        };
    }
}

