package be.ucll.model;

import static be.ucll.util.Validation.throwDomainException;
import static be.ucll.util.Validation.validateNegativeNumber;
import static be.ucll.util.Validation.validateNonEmptyString;
import static be.ucll.util.Validation.validatePublicationYear;


public abstract class Publication {
    private String title;
    private int publicationYear;
    private int availableCopies;
    
    public Publication(String title, int publicationYear, int availableCopies) {
        setTitle(title);
        setPublicationYear(publicationYear);
        setAvailableCopies(availableCopies);
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        validateNonEmptyString(title, "Title is required.");
        this.title = title;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        validatePublicationYear(publicationYear, "Publication year cannot be negative nor in the future.");
        this.publicationYear = publicationYear;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        validateNegativeNumber(availableCopies, "Available copies cannot be negative.");
        this.availableCopies = availableCopies;
    }

    public void lendPublication() {
        if(hasAvailableCopies()) {
            setAvailableCopies(getAvailableCopies() - 1);
        } else {
            throwDomainException("No available copies left for publication.");
        }
    }

    public void returnPublication() {
        setAvailableCopies(getAvailableCopies() + 1);
    }

    public boolean hasAvailableCopies() {
        return getAvailableCopies() > 0;
    }
}
