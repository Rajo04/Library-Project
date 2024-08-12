package be.ucll.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import be.ucll.repository.PublicationRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class PublicationIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PublicationRepository publicationRepository;

    @AfterEach
    public void resetData() {
        publicationRepository.resetRepositoryData();
    }

    @Test
    public void givenPublications_whenGetPublicationByTitleAndType_thenCorrectPublicationsAreReturned() {
        webTestClient.get().uri("/publications?title=The&type=magazine").exchange().expectStatus().isOk().expectBody()
                .json("[\n" +
                        "  {\n" +
                        "    \"title\": \"The Guardian\",\n" +
                        "    \"publicationYear\": 2010,\n" +
                        "    \"availableCopies\": 9,\n" +
                        "    \"editor\": \"James Barnes\",\n" +
                        "    \"issn\": \"12345678\"\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"title\": \"The New York Times\",\n" +
                        "    \"publicationYear\": 2014,\n" +
                        "    \"availableCopies\": 10,\n" +
                        "    \"editor\": \"Suzanne Collins\",\n" +
                        "    \"issn\": \"12345671\"\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"title\": \"The Washington Post\",\n" +
                        "    \"publicationYear\": 2011,\n" +
                        "    \"availableCopies\": 10,\n" +
                        "    \"editor\": \"Josh Jefferson\",\n" +
                        "    \"issn\": \"12345672\"\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"title\": \"The Wall Street Journal\",\n" +
                        "    \"publicationYear\": 2008,\n" +
                        "    \"availableCopies\": 10,\n" +
                        "    \"editor\": \"Josh Jefferson\",\n" +
                        "    \"issn\": \"12345673\"\n" +
                        "  }\n" +
                        "]");
    }

    @Test
    public void givenPublications_whenGetAvailableCopies_thenPublicationsWithEqualOrMoreAvailableCopiesAreReturned() {
        webTestClient.get().uri("/publications/stock/9").exchange().expectStatus().isOk().expectBody()
                .json("[\n" +
                        "  {\n" +
                        "    \"title\": \"Vikings\",\n" +
                        "    \"publicationYear\": 2010,\n" +
                        "    \"availableCopies\": 10,\n" +
                        "    \"author\": \"Arthur\",\n" +
                        "    \"isbn\": \"9783161484100\"\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"title\": \"Annabel\",\n" +
                        "    \"publicationYear\": 2014,\n" +
                        "    \"availableCopies\": 10,\n" +
                        "    \"author\": \"James Barnes\",\n" +
                        "    \"isbn\": \"9783161484140\"\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"title\": \"The Hunger Games\",\n" +
                        "    \"publicationYear\": 2011,\n" +
                        "    \"availableCopies\": 10,\n" +
                        "    \"author\": \"Suzanne collins\",\n" +
                        "    \"isbn\": \"9783161488140\"\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"title\": \"Franklin Brothers\",\n" +
                        "    \"publicationYear\": 2008,\n" +
                        "    \"availableCopies\": 10,\n" +
                        "    \"author\": \"Josh Jefferson\",\n" +
                        "    \"isbn\": \"9783169484140\"\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"title\": \"The Guardian\",\n" +
                        "    \"publicationYear\": 2010,\n" +
                        "    \"availableCopies\": 9,\n" +
                        "    \"editor\": \"James Barnes\",\n" +
                        "    \"issn\": \"12345678\"\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"title\": \"The New York Times\",\n" +
                        "    \"publicationYear\": 2014,\n" +
                        "    \"availableCopies\": 10,\n" +
                        "    \"editor\": \"Suzanne Collins\",\n" +
                        "    \"issn\": \"12345671\"\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"title\": \"The Washington Post\",\n" +
                        "    \"publicationYear\": 2011,\n" +
                        "    \"availableCopies\": 10,\n" +
                        "    \"editor\": \"Josh Jefferson\",\n" +
                        "    \"issn\": \"12345672\"\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"title\": \"The Wall Street Journal\",\n" +
                        "    \"publicationYear\": 2008,\n" +
                        "    \"availableCopies\": 10,\n" +
                        "    \"editor\": \"Josh Jefferson\",\n" +
                        "    \"issn\": \"12345673\"\n" +
                        "  }\n" +
                        "]");
    }
}
