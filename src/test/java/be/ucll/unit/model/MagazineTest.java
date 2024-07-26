package be.ucll.unit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import be.ucll.model.DomainException;
import be.ucll.model.Magazine;

public class MagazineTest {

    @Test
    public void givenValidValues_whenCreatingMagazine_thenMagazineIsCreated() {
        Magazine magazine = new Magazine("Flair", "Jan", "03785955", 2011, 10);

        assertEquals("Flair", magazine.getTitle());
        assertEquals("Jan", magazine.getEditor());
        assertEquals("03785955", magazine.getIssn());
        assertEquals(2011, magazine.getPublicationYear());
    }

    @Test
    public void givenInvalidTitle_whenCreatingMagazine_thenErrorThrown() {
        String invalidTitle = " ";
        assertThrows(DomainException.class,
        () -> new Magazine(invalidTitle, "Jan", "03785955", 2011, 10),
        "Title is required.");
    }

    @Test
    public void givenEmptyTitle_whenCreatingMagazine_thenErrorThrown() {
        String emptyTitle = null;
        assertThrows(DomainException.class,
        () -> new Magazine(emptyTitle, "Jan", "03785955", 2011, 10),
        "Title is required.");
    }

    @Test // TODO: Don't use "invalid", use "Blank" or "Empty" as name
    public void givenInvalidEditor_whenCreatingMagazine_thenErrorThrown() {
        String invalidEditor = " ";
        assertThrows(DomainException.class,
        () -> new Magazine("Flair", invalidEditor, "03785955", 2011, 10),
        "Editor is required.");
    }

    @Test // TODO: Don't use "empty", use null
    public void givenEmptyEditor_whenCreatingMagazine_thenErrorThrown() {
        String emptyEditor = null;
        assertThrows(DomainException.class,
        () -> new Magazine("Flair", emptyEditor, "03785955", 2011, 10),
        "Editor is required.");
    }

    @Test
    public void givenInvalidISSN_whenCreatingMagazine_thenErrorThrown() {
        String invalidISSN = " ";
        assertThrows(DomainException.class,
        () -> new Magazine("Flair", "Jan", invalidISSN, 2011, 10),
        "ISSN is required.");
    }
    
    @Test
    public void givenEmptyISSN_whenCreatingMagazine_thenErrorThrown() {
        String emptyISSN = null;
        assertThrows(DomainException.class,
        () -> new Magazine("Flair", "Jan", emptyISSN, 2011, 10),
        "ISSN is required.");
    }

    @Test
    public void givenTooShortISSN_whenCreatingMagazine_thenErrorThrown() {
        String tooShortISSN = "1234567";
        assertThrows(DomainException.class,
        () -> new Magazine("Flair", "Jan", tooShortISSN, 2011, 10),
        "ISSN must be exact 8 characters.");
    }

    @Test
    public void givenTooLongISSN_whenCreatingMagazine_thenErrorThrown() {
        String tooLongISSN = "123456789";
        assertThrows(DomainException.class,
        () -> new Magazine("Flair", "Jan", tooLongISSN, 2011, 10),
        "ISSN must be exact 8 characters.");
    }

    @Test
    public void givenNegativePublicationYear_whenCreatingMagazine_thenErrorThrown() {
        int negativePublicationYear = -2015; // TODO: Use boundary values (i.e. -1)
        assertThrows(DomainException.class,
        () -> new Magazine("Flair", "Jan", "03785955", negativePublicationYear, 10),
        "Publication year must be a positive integer.");
    }

    @Test
    public void givenFuturePublicationYear_whenCreatingMagazine_thenErrorThrown() {
        int futurePublicationYear = 2048; // TODO: Use boundary values (i.e. current year + 1)
        assertThrows(DomainException.class,
        () -> new Magazine("Flair", "Jan", "03785955", futurePublicationYear, 10),
        "Publication year cannot be in the future.");
    }

    @Test
    public void givenNegativeAvailableCopies_whenCreatingMagazine_thenErrorIsThrown() {
        int negativeAvailableCopies = -3; // TODO: Use boundary values (i.e. -1)
        assertThrows(DomainException.class,
        () -> new Magazine("Flair", "Jan", "03785955", 2011, negativeAvailableCopies),
        "Available copies cannot be negative.");
    }
}
