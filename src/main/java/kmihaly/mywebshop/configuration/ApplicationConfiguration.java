package kmihaly.mywebshop.configuration;


import kmihaly.mywebshop.domain.model.item.Brand;
import kmihaly.mywebshop.domain.model.item.Genre;
import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.domain.model.item.Type;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import kmihaly.mywebshop.repository.ItemRepository;
import kmihaly.mywebshop.repository.PurchaseRepository;
import kmihaly.mywebshop.repository.UserRepository;
import kmihaly.mywebshop.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


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
    public PurchaseService purchaseService (PurchaseRepository purchaseRepository, UserRepository userRepository) { return new DAOPurchaseService(purchaseRepository, userRepository); }

    @Bean
    public CommandLineRunner commandLineRunner(ItemRepository itemRepository, UserRepository userRepository) {
        return args -> {

            userRepository.save(new User("usern", "firsn", "lastn", "mail", "ad", "psw", UserType.REGISTERED));
            userRepository.save(new User("usern2", "firsn2", "lastn2", "mail2", "ad2", "psw2", UserType.REGISTERED));

            Item item = new Item("nam2e", "2", Brand.ADIDAS, 1, 0, Genre.MEN, Type.JEAN,"/img/jeanS01.jpg","/img/jeanB01.jpg");
            Item item2 = new Item("nam3e", "3", Brand.CONVERSE, 1, 1, Genre.MEN, Type.SHIRT,"/img/tshirtS01.jpg","/img/tshirtB01.jpg");
            Item item3 = new Item("nam4e", "4", Brand.NIKE, 1, 1, Genre.WOMEN, Type.SHORTS,"/img/shortsS01.jpg","/img/shortsB01.jpg");
            Item item4 = new Item("nam5e", "2", Brand.ZARA, 1, 1, Genre.WOMEN, Type.SOCKS,"/img/socksS01.png","/img/socksB01.png");
            Item item5 = new Item("nam6e", "1", Brand.NIKE, 1, 1, Genre.WOMEN, Type.SUIT,"/img/suitS01.png","/img/suitB01.png");

            itemService(itemRepository).addItem(item);
            itemService(itemRepository).addItem(item2);
            itemService(itemRepository).addItem(item3);
            itemService(itemRepository).addItem(item4);
            itemService(itemRepository).addItem(item5);

        };
    }
}

