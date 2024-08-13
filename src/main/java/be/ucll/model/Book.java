package be.ucll.model;

import static be.ucll.util.Validation.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Book extends Publication {
    @NotBlank(message = "Author is required.")
    private String author;
    @Size(min = 13, max = 13, message = "ISBN is required to have 13 digits and cannot be empty.")
    @NotBlank(message = "ISBN is required to have 13 digits and cannot be emtpy.")
    private String isbn;

    public Book(String title, String author, String isbn, int publicationYear, int availableCopies) {
        super(title, publicationYear, availableCopies);
        setAuthor(author);
        setIsbn(isbn);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "Book [author=" + author + ", isbn=" + isbn + ", getTitle=" + getTitle() + ", getPublicationYear="
                + getPublicationYear() + ", getAvailableCopies=" + getAvailableCopies() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Book other = (Book) obj;
        if (isbn == null) {
            if (other.isbn != null)
                return false;
        } else if (!isbn.equals(other.isbn))
            return false;
        return true;
    }
}
