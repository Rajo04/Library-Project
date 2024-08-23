package be.ucll.service;

import java.util.List;

import be.ucll.repository.UserRepository;
import org.springframework.stereotype.Service;

import be.ucll.model.Loan;
import be.ucll.model.ServiceException;
import be.ucll.repository.LoanRepository;
import be.ucll.repository.UserRepositoryImpl;

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
        if (!userRepository.existByEmail(email)) {
            throw new ServiceException("User with that email does not exist");
        }

        return this.loanRepository.findLoansByUser(email, onlyActive);
    }

    public String deleteLoansForUserByEmail(String email) {
        if (userRepository.findByEmail(email) == null) {
            throw new ServiceException("User does not exist.");
        }
        if (!loanRepository.findLoansByUser(email, true).isEmpty()) {
            throw new ServiceException("User has active loans.");
        }
        if (loanRepository.findLoansByUser(email, false).isEmpty()) {
            throw new ServiceException("User has no loans.");
        }
        loanRepository.deleteLoansForUserByEmail(email);
        return "Loans of user are successfully deleted.";
    }
}
