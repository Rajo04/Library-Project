package be.ucll.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import be.ucll.repository.LoanRepository;
import be.ucll.repository.PublicationRepository;
import be.ucll.repository.UserRepository;
import be.ucll.service.LoanService;
import be.ucll.service.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class UserIntegrationTest {

        @Autowired
        private WebTestClient webTestClient;

        private UserService userService;

        @BeforeEach
        void init() {
                UserRepository userRepository = new UserRepository();
                this.userService = new UserService(new UserRepository(),
                                new LoanService(new LoanRepository(userRepository, new PublicationRepository()),
                                                userRepository));
        }

        @Test
        public void givenUsers_whenGetUsers_thenUsersAreReturned() {
                webTestClient.get().uri("/users").exchange().expectStatus().isOk().expectBody().json(
                                "[\n" +
                                                "  {\n" +
                                                "    \"name\": \"John Doe\",\n" +
                                                "    \"age\": 56,\n" +
                                                "    \"email\": \"john.doe@ucll.be\",\n" +
                                                "    \"password\": \"john1234\"\n" +
                                                "  },\n" +
                                                "  {\n" +
                                                "    \"name\": \"Jane Toe\",\n" +
                                                "    \"age\": 30,\n" +
                                                "    \"email\": \"jane.toe@ucll.be\",\n" +
                                                "    \"password\": \"jane1234\"\n" +
                                                "  },\n" +
                                                "  {\n" +
                                                "    \"name\": \"Jack Doe\",\n" +
                                                "    \"age\": 5,\n" +
                                                "    \"email\": \"jack.doe@ucll.be\",\n" +
                                                "    \"password\": \"jack1234\"\n" +
                                                "  },\n" +
                                                "  {\n" +
                                                "    \"name\": \"Sarah Doe\",\n" +
                                                "    \"age\": 4,\n" +
                                                "    \"email\": \"sarah.doe@ucll.be\",\n" +
                                                "    \"password\": \"sarah1234\"\n" +
                                                "  }\n" +
                                                "]");
        }

        @Test
        public void givenUsers_whenGetAdults_thenAdultsAreReturned() {
                webTestClient.get().uri("/users/adults").exchange().expectStatus().isOk().expectBody().json(
                                "[\n" +
                                                "    {\n" +
                                                "        \"name\": \"John Doe\",\n" +
                                                "        \"age\": 56,\n" +
                                                "        \"email\": \"john.doe@ucll.be\",\n" +
                                                "        \"password\": \"john1234\"\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "        \"name\": \"Jane Toe\",\n" +
                                                "        \"age\": 30,\n" +
                                                "        \"email\": \"jane.toe@ucll.be\",\n" +
                                                "        \"password\": \"jane1234\"\n" +
                                                "    }\n" +
                                                "]");
        }

        @Test
        public void givenUsers_whenGetUsersWithinAgeRange_thenUsersWithinAgeRangeAreReturned() {
                webTestClient.get().uri("/users/age/{min}/{max}").exchange().expectStatus().isOk().expectBody().json(
                                "[\n" +
                                                "  {\n" +
                                                "    \"name\": \"John Doe\",\n" +
                                                "    \"age\": 56,\n" +
                                                "    \"email\": \"john.doe@ucll.be\",\n" +
                                                "    \"password\": \"john1234\"\n" +
                                                "  },\n" +
                                                "  {\n" +
                                                "    \"name\": \"Jane Toe\",\n" +
                                                "    \"age\": 30,\n" +
                                                "    \"email\": \"jane.toe@ucll.be\",\n" +
                                                "    \"password\": \"jane1234\"\n" +
                                                "  },\n" +
                                                "  {\n" +
                                                "    \"name\": \"Jack Doe\",\n" +
                                                "    \"age\": 5,\n" +
                                                "    \"email\": \"jack.doe@ucll.be\",\n" +
                                                "    \"password\": \"jack1234\"\n" +
                                                "  },\n" +
                                                "  {\n" +
                                                "    \"name\": \"Sarah Doe\",\n" +
                                                "    \"age\": 4,\n" +
                                                "    \"email\": \"sarah.doe@ucll.be\",\n" +
                                                "    \"password\": \"sarah1234\"\n" +
                                                "  }\n" +
                                                "]");
        }

        @Test
        public void givenUsers_whenGetLoansByEmail_thenCorrectLoansForEmailAreReturned() {
                webTestClient.get().uri("/users/age/{min}/{max}").exchange().expectStatus().isOk().expectBody().json(
                                "[\n" +
                                                "  {\n" +
                                                "    \"startDate\": \"2024-08-09\",\n" +
                                                "    \"endDate\": \"2024-08-10\",\n" +
                                                "    \"user\": {\n" +
                                                "      \"name\": \"John Doe\",\n" +
                                                "      \"age\": 56,\n" +
                                                "      \"email\": \"john.doe@ucll.be\",\n" +
                                                "      \"password\": \"john1234\"\n" +
                                                "    },\n" +
                                                "    \"publications\": [\n" +
                                                "      {\n" +
                                                "        \"title\": \"Vikings\",\n" +
                                                "        \"publicationYear\": 2010,\n" +
                                                "        \"availableCopies\": 10,\n" +
                                                "        \"author\": \"Arthur\",\n" +
                                                "        \"isbn\": \"9783161484100\"\n" +
                                                "      }\n" +
                                                "    ],\n" +
                                                "    \"active\": false\n" +
                                                "  },\n" +
                                                "  {\n" +
                                                "    \"startDate\": \"2024-08-11\",\n" +
                                                "    \"endDate\": null,\n" +
                                                "    \"user\": {\n" +
                                                "      \"name\": \"John Doe\",\n" +
                                                "      \"age\": 56,\n" +
                                                "      \"email\": \"john.doe@ucll.be\",\n" +
                                                "      \"password\": \"john1234\"\n" +
                                                "    },\n" +
                                                "    \"publications\": [\n" +
                                                "      {\n" +
                                                "        \"title\": \"The Guardian\",\n" +
                                                "        \"publicationYear\": 2010,\n" +
                                                "        \"availableCopies\": 9,\n" +
                                                "        \"editor\": \"James Barnes\",\n" +
                                                "        \"issn\": \"12345678\"\n" +
                                                "      }\n" +
                                                "    ],\n" +
                                                "    \"active\": true\n" +
                                                "  }\n" +
                                                "]");
        }
}
