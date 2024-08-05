package be.ucll.service;

import java.util.List;

import org.springframework.stereotype.Service;

import be.ucll.model.Loan;
import be.ucll.model.ServiceException;
import be.ucll.repository.LoanRepository;
import be.ucll.repository.UserRepository;

@Service
public class LoanService {
    private LoanRepository loanRepository;
    private UserRepository userRepository;

    public LoanService(LoanRepository loanRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
    }

    public List<Loan> getLoansByUser(String email, boolean onlyActive) {
        // First checks if the user exists
        if (!userRepository.userExists(email)) {
            throw new ServiceException("User with that email does not exist");
        }

        return this.loanRepository.findLoansByUser(email, onlyActive);
    }
}
