package be.ucll.controller;

import org.springframework.web.bind.annotation.RestController;

import be.ucll.model.User;
import be.ucll.service.UserService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/users")
public class UserRestController {
    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/adults")
    public List<User> getAllAdults() {
        return userService.getAllAdults();
    }

    @GetMapping("/age/{min}/{max}")
    public List<User> getUserByAgeRange(@PathVariable("min") int min, @PathVariable("max") int max) {
        return userService.getUsersByAgeRange(min, max);
    }
}
