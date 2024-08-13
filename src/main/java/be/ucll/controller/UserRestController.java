package be.ucll.controller;

import org.springframework.web.bind.annotation.RestController;

import be.ucll.model.DomainException;
import be.ucll.model.Loan;
import be.ucll.model.ServiceException;
import be.ucll.model.User;
import be.ucll.service.LoanService;
import be.ucll.service.UserService;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/users")
public class UserRestController {
    private UserService userService;
    private LoanService loanService;

    public UserRestController(UserService userService, LoanService loanService) {
        this.userService = userService;
        this.loanService = loanService;
    }

    @GetMapping
    public List<User> getAllUsers(@RequestParam(value = "name", required = false) String name) {
        if (name == null || name.isBlank()) {
            return userService.getAllUsers();
        }
        return userService.getUsersByName(name);
    }

    @GetMapping("/adults")
    public List<User> getAllAdults() {
        return userService.getAllAdultUsers();
    }

    @GetMapping("/age/{min}/{max}")
    public List<User> getUserByAgeRange(@PathVariable("min") int min, @PathVariable("max") int max) {
        return userService.getUsersByAgeRange(min, max);
    }

    @GetMapping("/{email}/loans")
    public List<Loan> getLoansByUser(@PathVariable String email,
            @RequestParam(value = "onlyActive", required = false, defaultValue = "false") boolean onlyActive) {
        return loanService.getLoansByUser(email, onlyActive);
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping("/{email}")
    public User updateUserByEmail(@PathVariable String email, @Valid @RequestBody User user) {
        return userService.updateUserByEmail(email, user);
    }

    @DeleteMapping("/{email}/loans")
    public String deleteLoansForUserByEmail(@PathVariable String email) {
        return loanService.deleteLoansForUserByEmail(email);
    }

    @DeleteMapping("/{email}")
    public String deleteUserByEmail(@PathVariable String email) {
        return userService.deleteUserByEmail(email);
    }
}
