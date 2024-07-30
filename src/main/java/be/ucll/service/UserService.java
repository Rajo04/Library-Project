package be.ucll.service;

import java.util.List;

import org.springframework.stereotype.Service;

import be.ucll.model.ServiceException;
import be.ucll.model.User;
import be.ucll.repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.allUsers();
    }

    public List<User> getAllAdults() {
        return userRepository.usersOlderThan(18);
    }

    public List<User> getUsersByAgeRange(int min, int max) {
        if(min > max) {
            throw new ServiceException("Minimum age cannot be greater than maximum age.");
        }
        if(0 > min || max > 150) {
            throw new ServiceException("Invalid age range. Age must be between 0 and 150.");
        }
        return userRepository.findUsersByAgeRange(min, max);
    }

    public List<User> getUsersByName(String name) {
        List<User> usersWithName = userRepository.usersByName(name);
        
        if(usersWithName.size() == 0) {
            throw new ServiceException("No users with that name found.");
        }
        return usersWithName;
    }
}