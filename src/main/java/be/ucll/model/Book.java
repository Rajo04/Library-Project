package be.ucll.model;

import static be.ucll.util.Validation.*;

public class Book extends Publication {
    private String author;
    private String isbn;

    public Book(String title, String author, String isbn, int publicationYear, int availableCopies){
        super(title, publicationYear, availableCopies);
        setAuthor(author);
        setIsbn(isbn);
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        validateNonEmptyString(author, "Author is required.");
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        validateIsbn(isbn, "ISBN is required to have 13 digits and cannot be empty.");
        this.isbn = isbn;
    }


    @Override
    public String toString() {
        return "Book [author=" + author + ", isbn=" + isbn + ", getTitle=" + getTitle() + ", getPublicationYear="
                + getPublicationYear() + ", getAvailableCopies=" + getAvailableCopies() + "]";
    }
}
