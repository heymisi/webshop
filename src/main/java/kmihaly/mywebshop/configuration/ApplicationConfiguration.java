package kmihaly.mywebshop.configuration;


import kmihaly.mywebshop.domain.model.item.*;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import kmihaly.mywebshop.repository.*;
import kmihaly.mywebshop.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import static org.slf4j.LoggerFactory.getLogger;


@Configuration
public class ApplicationConfiguration {

    @Bean
    public ItemService itemService(ItemRepository itemRepository, SelectedItemRepository selectedItemRepository, UserRepository userRepository, UserBagRepository userBagRepository) {
        return new DAOItemService(itemRepository, selectedItemRepository, userRepository, userBagRepository);
    }

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new DAOUserService(userRepository);
    }


    @Bean
    public PurchaseService purchaseService(PurchaseRepository purchaseRepository, UserRepository userRepository, SelectedItemRepository selectedItemRepository, ItemRepository itemRepository, DAOItemService itemService, UserBagRepository userBagRepository) {
        return new DAOPurchaseService(purchaseRepository, userRepository, selectedItemRepository, itemRepository, itemService, userBagRepository);
    }


    @Bean
    public CommandLineRunner commandLineRunner(ItemRepository itemRepository, UserRepository userRepository, SelectedItemRepository selectedItemRepository, UserBagRepository userBagRepository) {
        return args -> {
            if (userRepository.findAll().isEmpty()) {
                User user1 = new User("usern", "firsn", "lastn",
                        "mail", "ad", "date", "psw", UserType.USER);
                userRepository.save(user1);
                User user2 = new User("usern2", "firsn2", "lastn2",
                        "mail2", "ad2", "date", "psw2", UserType.ADMIN);
                userRepository.save(user2);
                userBagRepository.save(new UserBag(user1));
                userBagRepository.save(new UserBag(user2));
            }

            if (itemRepository.findAll().isEmpty()) {

                itemRepository.save(new Item("Jean 01", "Its a very good product\n"
                        , Brand.ADIDAS,
                        1, 100, Genre.MEN, Type.JEAN, "/img/jeanS01.jpg", "/img/jeanB01.jpg"));
                itemRepository.save(new Item("Suit 01", "Its a very good product\n"
                        , Brand.NIKE,
                        2, 100, Genre.MEN, Type.SUIT, "/img/suitS01.png", "/img/suitB01.png"));
                itemRepository.save(new Item("Socks 01", "Its a very good product\n"
                        , Brand.ZARA,
                        3, 100, Genre.MEN, Type.SOCKS, "/img/socksS01.png", "/img/socksB01.png"));
                itemRepository.save(new Item("T-shirt 01", "Its a very good product\n"
                        , Brand.CONVERSE,
                        4, 100, Genre.MEN, Type.SHIRT, "/img/tshirtS01.jpg", "/img/tshirtB01.jpg"));
                itemRepository.save(new Item("Shorts 01", "Its a very good product\n"
                        , Brand.NIKE,
                        5, 100, Genre.MEN, Type.SHORTS, "/img/shortsS01.jpg", "/img/shortsB01.jpg"));


                itemRepository.save(new Item("Jean 02", "Its a very good product\n"
                        , Brand.ADIDAS,
                        6, 100, Genre.MEN, Type.JEAN, "/img/jeanS02.png", "/img/jeanB02.png"));
                itemRepository.save(new Item("Suit 02", "Its a very good product\n"
                        , Brand.NIKE,
                        7, 100, Genre.MEN, Type.SUIT, "/img/suitS02.png", "/img/suitB02.png"));
                itemRepository.save(new Item("Socks 02", "Its a very good product\n"
                        , Brand.ZARA,
                        8, 100, Genre.MEN, Type.SOCKS, "/img/socksS02.png", "/img/socksB02.png"));
                itemRepository.save(new Item("T-shirt 02", "Its a very good product\n"
                        , Brand.CONVERSE,
                        9, 100, Genre.MEN, Type.SHIRT, "/img/tshirtS02.png", "/img/tshirtB02.png"));
                itemRepository.save(new Item("Shorts 02", "Its a very good product\n"
                        , Brand.NIKE,
                        10, 100, Genre.MEN, Type.SHORTS, "/img/shortsS02.png", "/img/shortsB02.png"));


                itemRepository.save(new Item("Jean 03", "Its a very good product\n"
                        , Brand.ADIDAS,
                        11, 100, Genre.WOMEN, Type.JEAN, "/img/jeanS03.png", "/img/jeanB03.png"));
                itemRepository.save(new Item("Suit 03", "Its a very good product\n"
                        , Brand.NIKE,
                        12, 100, Genre.WOMEN, Type.SUIT, "/img/suitS03.png", "/img/suitB03.png"));
                itemRepository.save(new Item("Socks 03", "Its a very good product\n"
                        , Brand.ZARA,
                        13, 100, Genre.WOMEN, Type.SOCKS, "/img/socksS03.png", "/img/socksB03.png"));
                itemRepository.save(new Item("T-shirt 03", "Its a very good product\n"
                        , Brand.CONVERSE,
                        14, 100, Genre.WOMEN, Type.SHIRT, "/img/tshirtS03.png", "/img/tshirtB03.png"));
                itemRepository.save(new Item("Shorts 03", "Its a very good product\n"
                        , Brand.NIKE,
                        15, 100, Genre.WOMEN, Type.SHORTS, "/img/shortsS03.png", "/img/shortsB03.png"));


                itemRepository.save(new Item("Jean 04", "Its a very good product\n"
                        , Brand.ADIDAS,
                        16, 100, Genre.WOMEN, Type.JEAN, "/img/jeanS04.png", "/img/jeanB04.png"));
                itemRepository.save(new Item("Suit 04", "Its a very good product\n"
                        , Brand.NIKE,
                        17, 100, Genre.WOMEN, Type.SUIT, "/img/suitS04.png", "/img/suitB04.png"));
                itemRepository.save(new Item("Socks 04", "Its a very good product\n"
                        , Brand.ZARA,
                        18, 100, Genre.WOMEN, Type.SOCKS, "/img/socksS04.png", "/img/socksB04.png"));
                itemRepository.save(new Item("T-shirt 04", "Its a very good product\n"
                        , Brand.CONVERSE,
                        19, 100, Genre.WOMEN, Type.SHIRT, "/img/tshirtS04.png", "/img/tshirtB04.png"));
                itemRepository.save(new Item("Shorts 04", "Its a very good product\n"
                        , Brand.NIKE,
                        20, 100, Genre.WOMEN, Type.SHORTS, "/img/shortsS04.png", "/img/shortsB04.png"));
            }

        };
    }
}

