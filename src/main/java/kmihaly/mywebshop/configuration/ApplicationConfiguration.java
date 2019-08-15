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


import java.util.ArrayList;

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
    public PurchaseService purchaseService(PurchaseRepository purchaseRepository, UserRepository userRepository, SelectedItemRepository selectedItemRepository) {
        return new DAOPurchaseService(purchaseRepository, userRepository, selectedItemRepository);
    }


    private static final Logger LOGGER = getLogger(ApplicationConfiguration.class);


    @Bean
    public CommandLineRunner commandLineRunner(ItemRepository itemRepository, UserRepository userRepository, SelectedItemRepository selectedItemRepository) {
        return args -> {
            if (userRepository.findAll().isEmpty()) {
                User user = new User("usern", "firsn", "lastn", "mail", "ad", "psw", UserType.REGISTERED);
                userRepository.save(user);
                userRepository.save(new User("usern2", "firsn2", "lastn2", "mail2", "ad2", "psw2", UserType.ADMIN));
            }
//            User user3 = new User("usern3", "firsn", "lastn", "mail", "ad", "psw", UserType.REGISTERED);
//            userRepository.save(user3);
//
//            Item nam2e = new Item("nam2e3", "2", Brand.ADIDAS, 1, 0, Genre.MEN, Type.JEAN, "/img/jeanS01.jpg", "/img/jeanB01.jpg");
//            itemRepository.save(nam2e);
//            SelectedItem selectedItem1 = new SelectedItem(nam2e, 2);
//            SelectedItem selectedItem2 = new SelectedItem(nam2e, 3);
//            selectedItemRepository.save(selectedItem1);
//            selectedItemRepository.save(selectedItem2);
//            user3.addItem(selectedItem1);
//            user3.addItem(selectedItem2);
//            userRepository.save(user3);

//            itemRepository.save(new Item("nam3e", "3", Brand.CONVERSE, 1, 1, Genre.MEN, Type.SHIRT, "/img/tshirtS01.jpg", "/img/tshirtB01.jpg"));
//            itemRepository.save(new Item("nam4e", "4", Brand.NIKE, 1, 1, Genre.WOMEN, Type.SHORTS, "/img/shortsS01.jpg", "/img/shortsB01.jpg"));

            if (itemRepository.findAll().isEmpty()) {
                Item asd = new Item("nam2e", "2", Brand.ADIDAS, 1, 0, Genre.MEN, Type.JEAN, "/img/jeanS01.jpg", "/img/jeanB01.jpg");
                itemRepository.save(asd);
              //epository.save(new Item("nam5e", "2", Brand.ZARA, 1, 1, Genre.WOMEN, Type.SOCKS, "/img/socksS01.png", "/img/socksB01.png"));
                itemRepository.save(new Item("nam6e", "1", Brand.NIKE, 1, 1, Genre.WOMEN, Type.SUIT, "/img/suitS01.png", "/img/suitB01.png"));
            }
//            ArrayList<SelectedItem> selectedItems = new ArrayList<>();
//            SelectedItem selectedItem1 = new SelectedItem(nam2e, 1);
//
//            selectedItemRepository.save(selectedItem1);
//
//            selectedItems.add(selectedItem1);
//
//            user.setSelectedItems(selectedItems);
//            userRepository.save(user);
//            User usern = userRepository.findByUserName("usern").get();
//            usern.setSelectedItems(new ArrayList<>());
//            userRepository.save(usern);

//            if (userRepository.findAll().isEmpty()) {
//                User user = new User("usern", "firsn", "lastn", "mail", "ad", "psw", UserType.REGISTERED);
//                userRepository.save(user);
//                userRepository.save(new User("usern2", "firsn2", "lastn2", "mail2", "ad2", "psw2", UserType.REGISTERED));
//            }
//
//            if (itemRepository.findAll().isEmpty()) {
//
//            }

        };
    }
}

