package be.ucll.integration;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import be.ucll.model.Book;
import be.ucll.model.Magazine;
import be.ucll.repository.PublicationRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class PublicationIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PublicationRepository publicationRepository;

    // Note: userRepository.resetRepositoryData() ❌ this is NOT a concern of the
    // user repository, only of the test logic, this method should be in the test
    // perhaps as a private helper method (see below),
    // OR use @DirtiesContext (on class or on test) to tell Spring Boot Test to
    // reset the spring container (this is slow/expensive)
    @AfterEach
    public void resetData() {
        // publicationRepository.resetRepositoryData(); ❌
        resetRepositoryData(); // ✅
    }

    private void resetRepositoryData() {
        this.publicationRepository.books = new ArrayList<>(List.of(
                new Book("Vikings", "Arthur", "9783161484100", 2010, 10),
                new Book("Annabel", "James Barnes", "9783161484140", 2014, 10),
                new Book("The Hunger Games", "Suzanne collins", "9783161488140", 2011, 10),
                new Book("Franklin Brothers", "Josh Jefferson", "9783169484140", 2008, 10)));

        this.publicationRepository.magazines = new ArrayList<>(List.of(
                new Magazine("The Guardian", "James Barnes", "12345678", 2010, 10),
                new Magazine("The New York Times", "Suzanne Collins", "12345671", 2014, 10),
                new Magazine("The Washington Post", "Josh Jefferson", "12345672", 2011, 10),
                new Magazine("The Wall Street Journal", "Josh Jefferson", "12345673", 2008, 10)));
    }

    @Test
    public void givenPublications_whenGetPublicationByTitleAndType_thenCorrectPublicationsAreReturned() {
        webTestClient.get().uri("/publications?title=The&type=magazine").exchange().expectStatus().isOk().expectBody()
                .json("""
                        [
                          {
                            "title": "The Guardian",
                            "publicationYear": 2010,
                            "availableCopies": 9,
                            "editor": "James Barnes",
                            "issn": "12345678"
                          },
                          {
                            "title": "The New York Times",
                            "publicationYear": 2014,
                            "availableCopies": 10,
                            "editor": "Suzanne Collins",
                            "issn": "12345671"
                          },
                          {
                            "title": "The Washington Post",
                            "publicationYear": 2011,
                            "availableCopies": 10,
                            "editor": "Josh Jefferson",
                            "issn": "12345672"
                          },
                          {
                            "title": "The Wall Street Journal",
                            "publicationYear": 2008,
                            "availableCopies": 10,
                            "editor": "Josh Jefferson",
                            "issn": "12345673"
                          }
                        ]
                        """);

    }

    @Test
    public void givenPublications_whenGetAvailableCopies_thenPublicationsWithEqualOrMoreAvailableCopiesAreReturned() {
        webTestClient.get().uri("/publications/stock/9").exchange().expectStatus().isOk().expectBody()
                .json("""
                            [
                              {
                                "title": "Vikings",
                                "publicationYear": 2010,
                                "availableCopies": 10,
                                "author": "Arthur",
                                "isbn": "9783161484100"
                              },
                              {
                                "title": "Annabel",
                                "publicationYear": 2014,
                                "availableCopies": 10,
                                "author": "James Barnes",
                                "isbn": "9783161484140"
                              },
                              {
                                "title": "The Hunger Games",
                                "publicationYear": 2011,
                                "availableCopies": 10,
                                "author": "Suzanne collins",
                                "isbn": "9783161488140"
                              },
                              {
                                "title": "Franklin Brothers",
                                "publicationYear": 2008,
                                "availableCopies": 10,
                                "author": "Josh Jefferson",
                                "isbn": "9783169484140"
                              },
                              {
                                "title": "The Guardian",
                                "publicationYear": 2010,
                                "availableCopies": 10,
                                "editor": "James Barnes",
                                "issn": "12345678"
                              },
                              {
                                "title": "The New York Times",
                                "publicationYear": 2014,
                                "availableCopies": 10,
                                "editor": "Suzanne Collins",
                                "issn": "12345671"
                              },
                              {
                                "title": "The Washington Post",
                                "publicationYear": 2011,
                                "availableCopies": 10,
                                "editor": "Josh Jefferson",
                                "issn": "12345672"
                              },
                              {
                                "title": "The Wall Street Journal",
                                "publicationYear": 2008,
                                "availableCopies": 10,
                                "editor": "Josh Jefferson",
                                "issn": "12345673"
                              }
                            ]
                        """);

    }
}
