package kmihaly.mywebshop.configuration;


import kmihaly.mywebshop.domain.model.item.*;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import kmihaly.mywebshop.repository.ItemRepository;
import kmihaly.mywebshop.repository.PurchaseRepository;
import kmihaly.mywebshop.repository.SelectedItemRepository;
import kmihaly.mywebshop.repository.UserRepository;
import kmihaly.mywebshop.service.*;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import static org.slf4j.LoggerFactory.getLogger;


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
    public PurchaseService purchaseService(PurchaseRepository purchaseRepository, UserRepository userRepository, SelectedItemRepository selectedItemRepository,ItemRepository itemRepository) {
        return new DAOPurchaseService(purchaseRepository, userRepository, selectedItemRepository, itemRepository);
    }


    private static final Logger LOGGER = getLogger(ApplicationConfiguration.class);


    @Bean
    public CommandLineRunner commandLineRunner(ItemRepository itemRepository, UserRepository userRepository, SelectedItemRepository selectedItemRepository) {
        return args -> {
            if (userRepository.findAll().isEmpty()) {
                userRepository.save(new User("usern", "firsn", "lastn",
                        "mail", "ad", "psw", UserType.USER));
                userRepository.save(new User("usern2", "firsn2", "lastn2",
                        "mail2", "ad2", "psw2", UserType.ADMIN));
            }

            if (itemRepository.findAll().isEmpty()) {
                itemRepository.save( new Item("name1", "1", Brand.ADIDAS,
                        1, 100, Genre.MEN, Type.JEAN, "/img/jeanS01.jpg", "/img/jeanB01.jpg"));
                itemRepository.save(new Item("name2", "2", Brand.NIKE,
                        1, 100, Genre.MEN, Type.SUIT, "/img/suitS01.png", "/img/suitB01.png"));
                itemRepository.save(new Item("name3", "3", Brand.ZARA,
                        1, 100, Genre.MEN, Type.SOCKS, "/img/socksS01.png", "/img/socksB01.png"));
                itemRepository.save(new Item("name4", "4", Brand.CONVERSE,
                        1, 100, Genre.WOMEN, Type.SHIRT, "/img/tshirtS01.jpg", "/img/tshirtB01.jpg"));
                itemRepository.save(new Item("name6", "6", Brand.NIKE,
                        1, 100, Genre.WOMEN, Type.SHORTS, "/img/shortsS01.jpg", "/img/shortsB01.jpg"));
            }

        };
    }
}

