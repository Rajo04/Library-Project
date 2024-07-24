package be.ucll.unit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import be.ucll.model.Book;
import be.ucll.model.DomainException;

public class BookTest {

    @Test
    public void givenValidValues_whenCreatingBook_thenBookIsCreated() {
        Book book = new Book("Vikings", "Arthur", "9783161484100", 2010);

        assertEquals("Vikings", book.getTitle());
        assertEquals("Arthur", book.getAuthor());
        assertEquals("9783161484100", book.getISBN());
        assertEquals(2010, book.getPublicationYear());
    }

    @Test
    public void givenInvalidTitle_whenCreatingBook_thenErrorThrown() {
        String invalidTitle = " ";
        assertThrows(DomainException.class,
        () -> new Book(invalidTitle, "Arthur", "9783161484100", 2010),
        "Name is required.");
    }

    @Test
    public void givenEmptyTitle_whenCreatingBook_thenErrorThrown() {
        String emptyTitle = null;
        assertThrows(DomainException.class,
        () -> new Book(emptyTitle, "Arthur", "9783161484100", 2010),
        "Name is required.");
    }

    @Test
    public void givenInvalidAuthor_whenCreatingBook_thenErrorThrown() {
        String invalidAuthor = " ";
        assertThrows(DomainException.class,
        () -> new Book("Vikings", invalidAuthor, "9783161484100", 2010),
        "Author is required.");
    }

    @Test
    public void givenEmptyAuthor_whenCreatingBook_thenErrorThrown() {
        String emptyAuthor = null;
        assertThrows(DomainException.class,
        () -> new Book("Vikings", emptyAuthor, "9783161484100", 2010),
        "Author is required.");
    }

    @Test
    public void givenTooShortISBN_whenCreatingBook_thenErrorThrown() {
        String tooShortISBN = "456465544512";
        assertThrows(DomainException.class,
        () -> new Book("Vikings", "Arthur", tooShortISBN, 2010),
        "ISBN is required to have 13 digits.");
    }

    @Test
    public void givenInvalidISBN_whenCreatingBook_thenErrorThrown() {
        String invalidISBN = " ";
        assertThrows(DomainException.class,
        () -> new Book("Vikings", "Arthur", invalidISBN, 2010),
        "ISBN is required.");
    }

    @Test
    public void givenEmptyISBN_whenCreatingBook_thenErrorThrown() {
        String emptyISBN = null;
        assertThrows(DomainException.class,
        () -> new Book("Vikings", "Arthur", emptyISBN, 2010),
        "ISBN is required.");
    }

    @Test
    public void givenNegativePublicationYear_whenCreatingBook_thenErrorThrown() {
        int negativePublicationYear = -2015;
        assertThrows(DomainException.class,
        () -> new Book("Vikings", "Arthur", "9783161484100", negativePublicationYear),
        "Publication year must be a positive integer.");
    }

    @Test
    public void givenFuturePublicationYear_whenCreatingBook_thenErrorThrown() {
        int futurePublicationYear = 2048;
        assertThrows(DomainException.class,
        () -> new Book("Vikings", "Arthur", "9783161484100", futurePublicationYear),
        "Publication year cannot be in the future.");
    }
}
