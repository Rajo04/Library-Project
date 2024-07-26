package be.ucll.model;

import static be.ucll.util.Validation.throwDomainException;

import java.time.LocalDate;
import java.util.List;

public class Loan {
    private LocalDate startDate;
    private LocalDate endDate;
    private User user;
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
        if(startDate == null) {
            throwDomainException("Start date is required.");
        }
        if(startDate.isAfter(LocalDate.now())) {
            throwDomainException("Start date cannot be in the future.");
        }
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        if(endDate == null) {
            throwDomainException("End date is required.");
        }
        if(endDate.isBefore(startDate)){
            throwDomainException("Start date cannot be after end date.");
        }
        if(endDate.isAfter(LocalDate.now())){
            throwDomainException("End date cannot be in the future.");
        }
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if(user == null) {
            throwDomainException("User is required.");
        }
        this.user = user;
    }

    public List<Publication> getPublications() {
        return publications;
    }

    public void setPublications(List<Publication> publications) {
        if(publications == null || publications.size() == 0) {
            throwDomainException("Publications are required.");
        }
        for(Publication publication: publications) {
            if(!publication.hasAvailableCopies()) {
                throwDomainException("Unable to lend publication. No copies available for." + publication.getTitle());
            }
        }
        for(Publication publication : publications) {
            publication.lendPublication();
        }
        this.publications = publications;
    }

    public void returnPublications() {
        for(Publication publication : this.publications) {
            publication.returnPublication();
        }
    }
}
