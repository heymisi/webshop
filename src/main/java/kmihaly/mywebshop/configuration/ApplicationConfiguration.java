package kmihaly.mywebshop.configuration;


import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.domain.model.user.UserType;
import kmihaly.mywebshop.repository.ItemRepository;
import kmihaly.mywebshop.repository.UserRepository;
import kmihaly.mywebshop.service.DAOItemService;
import kmihaly.mywebshop.service.DAOUserService;
import kmihaly.mywebshop.service.ItemService;
import kmihaly.mywebshop.service.UserService;
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
    public CommandLineRunner commandLineRunner(ItemRepository itemRepository, UserRepository userRepository) {
        return args -> {
            userRepository.save(new User("usern", "firsn", "lastn", "mail", "ad", "psw", UserType.REGISTERED));
            userRepository.save(new User("usern2", "firsn2", "lastn2", "mail2", "ad2", "psw2", UserType.REGISTERED));

            itemRepository.save(new Item("name", "desc", "a", 1, 1));

        };
    }
}

