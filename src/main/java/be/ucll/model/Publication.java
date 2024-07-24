package be.ucll.model;

import java.time.LocalDate;

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
        if(title == " " | title == null) {
            throw new DomainException("Title is required.");
        }
        this.title = title;
    }
    public int getPublicationYear() {
        return publicationYear;
    }
    public void setPublicationYear(int publicationYear) {
        if(publicationYear < 0){
            throw new DomainException("Publication year must be a positive integer.");
        } else if(publicationYear > LocalDate.now().getYear()) {
            throw new DomainException("Publication year cannot be in the future.");
        }
        this.publicationYear = publicationYear;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

}
