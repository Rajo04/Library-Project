package be.ucll.service;

import java.util.List;

import org.springframework.stereotype.Service;

import be.ucll.model.ServiceException;
import be.ucll.model.User;
import be.ucll.repository.LoanRepository;
import be.ucll.repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;
    private LoanRepository loanRepository;

    public UserService(UserRepository userRepository, LoanRepository loanRepository) {
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.allUsers();
    }

    public List<User> getAllAdultUsers() {
        return userRepository.usersOlderThan(17);
    }

    public List<User> getUsersByAgeRange(int min, int max) {
        if(min > max) {
            throw new ServiceException("Minimum age cannot be greater than maximum age.");
        }
        if(min < 0 || max > 150) {
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

    public User addUser(User user) {
        if(userRepository.findUserByEmail(user.getEmail()) != null){
            throw new ServiceException("User already exists");
        }
        return userRepository.addUser(user);
    }

    public User updateUserByEmail(String email, User user) {
        if(userRepository.findUserByEmail(email) == null) {
            throw new ServiceException("User does not exist.");
        }
        return userRepository.updateUserByEmail(email, user);
    }

    public String deleteLoansForUserByEmail(String email) {
        if(userRepository.findUserByEmail(email) == null) {
            throw new ServiceException("User does not exist.");
        }
        if(loanRepository.findLoansByUser(email, true) != null) {
            throw new ServiceException("User has active loans.");
        }
        if (loanRepository.findLoansByUser(email, false) == null) {
            throw new ServiceException("User has no loans.");
        }
        return userRepository.deleteLoansForUserByEmail(email);
    }
}