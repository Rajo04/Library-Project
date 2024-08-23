package be.ucll.service;

import java.util.List;

import be.ucll.repository.UserRepository;
import org.springframework.stereotype.Service;

import be.ucll.model.ServiceException;
import be.ucll.model.User;

@Service
public class UserService {
    private UserRepository userRepository;
    private LoanService loanService;

    public UserService(UserRepository userRepository, LoanService loanService) {
        this.userRepository = userRepository;
        this.loanService = loanService;
    }

    public List<User> getAllUsers() {
        return userRepository.allUsers();
    }

    public List<User> getAllAdultUsers() {
        return userRepository.usersOlderThan(17);
    }

    public List<User> getUsersByAgeRange(int min, int max) {
        if (min > max) {
            throw new ServiceException("Minimum age cannot be greater than maximum age.");
        }
        if (min < 0 || max > 150) {
            throw new ServiceException("Invalid age range. Age must be between 0 and 150.");
        }
        return userRepository.findUsersByAgeRange(min, max);
    }

    public List<User> getUsersByName(String name) {
        List<User> usersWithName = userRepository.usersByName(name);

        if (usersWithName.size() == 0) {
            throw new ServiceException("No users with that name found.");
        }
        return usersWithName;
    }

    public User addUser(User user) {
        if (userRepository.findUserByEmail(user.getEmail()) != null) {
            throw new ServiceException("User already exists");
        }
        return userRepository.addUser(user);
    }

    public User updateUserByEmail(String email, User user) {
        if (userRepository.findUserByEmail(email) == null) {
            throw new ServiceException("User does not exist.");
        }
        return userRepository.updateUserByEmail(email, user);
    }

    public String deleteUserByEmail(String email) {
        loanService.deleteLoansForUserByEmail(email);
        userRepository.deleteUserByEmail(email);
        return "User successfully deleted.";
    }
}
