package be.ucll.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.ucll.model.Book;
import be.ucll.model.Magazine;
import be.ucll.model.Publication;
import be.ucll.repository.PublicationRepository;
import be.ucll.service.PublicationService;

public class PublicationServiceTest {
    private PublicationService publicationService;
    
    @BeforeEach
    void init(){
        this.publicationService = new PublicationService(new PublicationRepository());
    }

    @Test
    void givenPublications_whenGetAllPublicationsWithoutFilters_thenAllPublicationsAreReturned() {
        List<Publication> expectedPublications = List.of(
            new Book("Vikings", "Arthur", "9783161484100", 2010, 10),
            new Book("Annabel", "James Barnes", "9783161484140", 2014, 10),
            new Book("The Hunger Games", "Suzanne collins", "9783161488140", 2011, 10),
            new Book("Franklin Brothers", "Josh Jefferson", "9783169484140", 2008, 10),
            new Magazine("The Guardian", "James Barnes", "12345678", 2010, 10),
            new Magazine("The New York Times", "Suzanne Collins", "12345671", 2014, 10),
            new Magazine("The Washington Post", "Josh Jefferson", "12345672", 2011, 10),
            new Magazine("The Wall Street Journal", "Josh Jefferson", "12345673", 2008, 10)
        );
        List<Publication> fetchedPublicationsWithoutFilters = this.publicationService.findPublicationsByTitleAndType(null, null);
        
        assertEquals(expectedPublications.size(), fetchedPublicationsWithoutFilters.size());
        assertIterableEquals(expectedPublications, fetchedPublicationsWithoutFilters);
    }

    @Test
    void givenPublications_whenGetAllPublicationsWithValidTitle_thenPublicationsWithPartiallyMatchingTitleAreReturned() {
        String titleSearchTerm = "The";
        List<Publication> expectedPublications = List.of(
            new Book("The Hunger Games", "Suzanne collins", "9783161488140", 2011, 10),
            new Book("Franklin Brothers", "Josh Jefferson", "9783169484140", 2008, 10),
            new Magazine("The Guardian", "James Barnes", "12345678", 2010, 10),
            new Magazine("The New York Times", "Suzanne Collins", "12345671", 2014, 10),
            new Magazine("The Washington Post", "Josh Jefferson", "12345672", 2011, 10),
            new Magazine("The Wall Street Journal", "Josh Jefferson", "12345673", 2008, 10)
        );
        List<Publication> fetchedPublicationsWithoutFilters = this.publicationService.findPublicationsByTitleAndType(titleSearchTerm, null);
        
        assertEquals(expectedPublications.size(), fetchedPublicationsWithoutFilters.size());
        assertIterableEquals(expectedPublications, fetchedPublicationsWithoutFilters);
    }

    @Test
    void givenPublications_whenGetAllPublicationsWithValidType_thenAllPublicationsOfTypeAreReturned() {
        String type = "Book";
        List<Publication> expectedPublications = List.of(
            new Book("Vikings", "Arthur", "9783161484100", 2010, 10),
            new Book("Annabel", "James Barnes", "9783161484140", 2014, 10),
            new Book("The Hunger Games", "Suzanne collins", "9783161488140", 2011, 10),
            new Book("Franklin Brothers", "Josh Jefferson", "9783169484140", 2008, 10)
            );
        List<Publication> fetchedPublicationsTypeFilter = this.publicationService.findPublicationsByTitleAndType(null, type);
        
        assertEquals(expectedPublications.size(), fetchedPublicationsTypeFilter.size());
        assertIterableEquals(expectedPublications, fetchedPublicationsTypeFilter);
    }

    @Test
    void givenPublications_whenGetAllPublicationsWithValidTitleAndType_thenAllPublicationsOfTypeAndPartiallyMatchingTitleAreReturned() {
        String titleSearchTerm = "The";
        String type = "book";
        List<Publication> expectedPublications = List.of(
            new Book("The Hunger Games", "Suzanne collins", "9783161488140", 2011, 10),
            new Book("Franklin Brothers", "Josh Jefferson", "9783169484140", 2008, 10)
            );
        List<Publication> fetchedPublicationsTitleAndTypeFiltered = this.publicationService.findPublicationsByTitleAndType(titleSearchTerm, type);
        
        assertEquals(expectedPublications.size(), fetchedPublicationsTitleAndTypeFiltered.size());
        assertIterableEquals(expectedPublications, fetchedPublicationsTitleAndTypeFiltered);
    }

    @Test
    void givenPublications_whenGetAllPublicationsWithNonMatchingTitle_thenEmptyListReturned() {
        String nonMatchingTitleSearchTerm = "YannickIsCool";
        
        List<Publication> fetchedPublicationsTitleFiltered = this.publicationService.findPublicationsByTitleAndType(nonMatchingTitleSearchTerm, null);
        
        assertNotNull(fetchedPublicationsTitleFiltered);
        assertTrue(fetchedPublicationsTitleFiltered.isEmpty());
    }

    @Test
    void givenPublications_whenGetAllPublicationsWithNonMatchingTyoe_thenEmptyListReturned() {
        String nonMatchingType = "Dvd";
        
        List<Publication> fetchedPublicationsTypeFiltered = this.publicationService.findPublicationsByTitleAndType(null, nonMatchingType);
        
        assertNotNull(fetchedPublicationsTypeFiltered);
        assertTrue(fetchedPublicationsTypeFiltered.isEmpty());
    }

    @Test
    void givenPublications_whenGetAllPublicationsWithEqualOrMoreAvailableCopies_thenCorrectPublicationsReturned() {
        int equalOrMoreAvailableCopies = 9;

        List<Publication> expectedPublications = List.of(
            new Book("Vikings", "Arthur", "9783161484100", 2010, 10),
            new Book("Annabel", "James Barnes", "9783161484140", 2014, 10),
            new Book("The Hunger Games", "Suzanne collins", "9783161488140", 2011, 10),
            new Book("Franklin Brothers", "Josh Jefferson", "9783169484140", 2008, 10),
            new Magazine("The Guardian", "James Barnes", "12345678", 2010, 10),
            new Magazine("The New York Times", "Suzanne Collins", "12345671", 2014, 10),
            new Magazine("The Washington Post", "Josh Jefferson", "12345672", 2011, 10),
            new Magazine("The Wall Street Journal", "Josh Jefferson", "12345673", 2008, 10)
        );

        List<Publication> fetchedPublicationsByAvailableCopies = this.publicationService.findPublicationsByAvailableCopies(equalOrMoreAvailableCopies);

        assertEquals(expectedPublications.size(), fetchedPublicationsByAvailableCopies.size());
        assertIterableEquals(expectedPublications, fetchedPublicationsByAvailableCopies);
    }


    @Test
    void givenPublications_whenNonMatchingOrMoreAvailableCopies_thenEmptyStringReturned() {
        int nonMatchingOrMoreAvailableCopies = 11;

        List<Publication> fetchedPublicationsByAvailableCopies = this.publicationService.findPublicationsByAvailableCopies(nonMatchingOrMoreAvailableCopies);

        assertNotNull(fetchedPublicationsByAvailableCopies);
        assertTrue(fetchedPublicationsByAvailableCopies.isEmpty());
    }
}