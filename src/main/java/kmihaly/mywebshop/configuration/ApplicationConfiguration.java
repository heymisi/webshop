package kmihaly.mywebshop.configuration;


import kmihaly.mywebshop.domain.model.item.*;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import kmihaly.mywebshop.repository.ItemRepository;
import kmihaly.mywebshop.repository.PurchaseRepository;
import kmihaly.mywebshop.repository.SelectedItemRepository;
import kmihaly.mywebshop.repository.UserRepository;
import kmihaly.mywebshop.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import static org.slf4j.LoggerFactory.getLogger;


@Configuration
public class ApplicationConfiguration {

    @Bean
    public ItemService itemService(ItemRepository itemRepository, SelectedItemRepository selectedItemRepository,UserRepository userRepository) {
        return new DAOItemService(itemRepository, selectedItemRepository, userRepository);
    }

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new DAOUserService(userRepository);
    }


    @Bean
    public PurchaseService purchaseService(PurchaseRepository purchaseRepository, UserRepository userRepository, SelectedItemRepository selectedItemRepository, ItemRepository itemRepository,DAOItemService itemService) {
        return new DAOPurchaseService(purchaseRepository, userRepository, selectedItemRepository, itemRepository, itemService);
    }


    @Bean
    public CommandLineRunner commandLineRunner(ItemRepository itemRepository, UserRepository userRepository, SelectedItemRepository selectedItemRepository) {
        return args -> {
            if (userRepository.findAll().isEmpty()) {
                userRepository.save(new User("usern", "firsn", "lastn",
                        "mail", "ad", "date", "psw", UserType.USER));
                userRepository.save(new User("usern2", "firsn2", "lastn2",
                        "mail2", "ad2", "date", "psw2", UserType.ADMIN));
            }

            if (itemRepository.findAll().isEmpty()) {
                Item name1 = new Item("name1", "BCI provides farming-practice training \n" +
                        "It promotes things like water efficiency and\n" +
                        "reducing the most harmful chemicals"
                        , Brand.ADIDAS,
                        10, 100, Genre.MEN, Type.JEAN, "/img/jeanS01.jpg", "/img/jeanB01.jpg");
                name1.setRate(1.1);
                itemRepository.save(name1);
                Item name2 = new Item("name2", "BCI provides farming-practice training \n" +
                        "It promotes things like water efficiency and\n" +
                        "reducing the most harmful chemicals"
                        , Brand.NIKE,
                        9, 100, Genre.MEN, Type.SUIT, "/img/suitS01.png", "/img/suitB01.png");
                name2.setRate(2.2);
                itemRepository.save(name2);
                Item name3 = new Item("name3", "BCI provides farming-practice training \n" +
                        "It promotes things like water efficiency and\n" +
                        "reducing the most harmful chemicals"
                        , Brand.ZARA,
                        8, 100, Genre.MEN, Type.SOCKS, "/img/socksS01.png", "/img/socksB01.png");
                name3.setRate(3.3);
                itemRepository.save(name3);
                Item name4 = new Item("name4", "BCI provides farming-practice training \n" +
                        "It promotes things like water efficiency and\n" +
                        "reducing the most harmful chemicals"
                        , Brand.CONVERSE,
                        107, 100, Genre.WOMEN, Type.SHIRT, "/img/tshirtS01.jpg", "/img/tshirtB01.jpg");
                name4.setRate(3.5);
                itemRepository.save(name4);
                itemRepository.save(new Item("name6", "6", Brand.NIKE,
                        106, 100, Genre.WOMEN, Type.SHORTS, "/img/shortsS01.jpg", "/img/shortsB01.jpg"));
            }

        };
    }
}

