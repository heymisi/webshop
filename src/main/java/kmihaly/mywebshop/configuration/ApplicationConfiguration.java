package kmihaly.mywebshop.configuration;


import kmihaly.mywebshop.domain.model.item.GenreType;
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

            Item item = new Item("nam2e", "2", "adidas", 1, 1, GenreType.MEN);
            Item item2 = new Item("nam3e", "3", "nike", 1, 1, GenreType.MEN);
            Item item3 = new Item("nam4e", "4", "converse", 1, 1, GenreType.WOMEN);
            Item item4 = new Item("nam5e", "2", "nike", 1, 1, GenreType.WOMEN);
            Item item5 = new Item("nam6e", "1", "converse", 1, 1, GenreType.WOMEN);

            itemService(itemRepository).addItem(item);
            itemService(itemRepository).addItem(item2);
            itemService(itemRepository).addItem(item3);
            itemService(itemRepository).addItem(item4);
            itemService(itemRepository).addItem(item5);

        };
    }
}

