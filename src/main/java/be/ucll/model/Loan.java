package be.ucll.model;

import static be.ucll.util.Validation.throwDomainException;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public class Loan {
    @NotNull(message = "Start date is required.")
    @PastOrPresent(message = "Start date cannot be in the future.")
    private LocalDate startDate;
    private LocalDate endDate;
    @NotNull(message = "User is required.")
    private User user;
    @NotNull(message = "Publications are required.")
    @Size(min = 1, message = "Publications are required.")
    private List<Publication> publications;

    public Loan(LocalDate starDate, LocalDate endDate, User user, List<Publication> publications) {
        setStartDate(starDate);
        setEndDate(endDate);
        setUser(user);
        setPublications(publications);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        if (endDate != null) {
            if (startDate != null && endDate.isBefore(startDate)) {
                throwDomainException("Start date cannot be after end date.");
            }
            if (endDate.isAfter(LocalDate.now())) {
                throwDomainException("End date cannot be in the future.");
            }
        }
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Publication> getPublications() {
        return publications;
    }

    public void setPublications(List<Publication> publications) {
        if (this.endDate == null) {
            for (Publication publication : publications) {
                if (!publication.hasAvailableCopies()) {
                    throwDomainException(
                            "Unable to lend publication. No copies available for." + publication.getTitle());
                }
            }
            for (Publication publication : publications) {
                publication.lendPublication();
            }
        }

        this.publications = publications;
    }

    public void returnPublications() {
        for (Publication publication : this.publications) {
            publication.returnPublication();
        }
        LocalDate today = LocalDate.now();
        setEndDate(today);
    }

    public boolean isActive() {
        return endDate == null;
    }
}
