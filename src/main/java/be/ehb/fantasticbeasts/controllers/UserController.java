package be.ehb.fantasticbeasts.controllers;

import be.ehb.fantasticbeasts.entities.User;
import be.ehb.fantasticbeasts.repo.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;



@Controller
public class UserController {
    private final UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @ModelAttribute("user")
    public User user(){
        return new User();
    }
}


