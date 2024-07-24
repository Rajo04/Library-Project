package be.ucll.model;

import java.time.LocalDate;


public class Book {
    private String title;
    private String author;
    private String ISBN;
    private int publicationYear;

    public Book(String title, String author, String ISBN, int publicationYear){
        setTitle(title);
        setAuthor(author);
        setISBN(ISBN);
        setPublicationYear(publicationYear);
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if(author == " " | author == null) {
            throw new DomainException("Author is required.");
        }
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        if(ISBN == " " | ISBN == null) {
            throw new DomainException("ISBN is required.");
        } else if(ISBN.length() != 13) {
            throw new DomainException("ISBN is required to have 13 digits.");
        }
        this.ISBN = ISBN;
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
}
