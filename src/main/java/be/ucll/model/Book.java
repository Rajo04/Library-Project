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
