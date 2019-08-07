/*
package kmihaly.mywebshop.controller;

import java.util.List;

import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.service.ItemService;
import kmihaly.mywebshop.service.UserService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AppController {

    private final UserService service;
    private final ItemService itemService;

    public AppController(UserService service, ItemService itemService) {
        this.service = service;
        this.itemService = itemService;
    }


    @GetMapping("/User")
    public List<User> getAll() {
        return service.listUsers();
    }

    @GetMapping("/Item")
    public List<Item> get() {
        return itemService.getRandomItems(1);
    }

    @GetMapping("/")
    public String main() {
        return "Main Menu ";
    }

    @GetMapping("/registration")
    public String registration() {

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(User userForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        return "redirect:/";
    }


}


*/
