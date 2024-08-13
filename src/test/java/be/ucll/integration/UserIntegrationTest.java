package be.ucll.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import be.ucll.model.User;
import be.ucll.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class UserIntegrationTest {

        @Autowired
        private WebTestClient webTestClient;

        @Autowired
        private UserRepository userRepository;


        // Note: userRepository.resetRepositoryData() ❌ this is NOT a concern of the user repository, only of the test logic, this method should be in the test perhaps as a private helper method (see below),
        //                      OR use @DirtiesContext (on class or on test) to tell Spring Boot Test to reset the spring container (this is slow/expensive)
        // @AfterEach
        // public void resetData() {
        //         userRepository.resetRepositoryData();
        // }

        // @AfterEach
        private void resetRepositoryData(){
                userRepository.users = new ArrayList<>(List.of(
                new User("John Doe", 56, "john.doe@ucll.be", "john1234"),
                new User("Jane Toe", 30, "jane.toe@ucll.be", "jane1234"),
                new User("Jack Doe", 5, "jack.doe@ucll.be", "jack1234"),
                new User("Sarah Doe", 4, "sarah.doe@ucll.be", "sarah1234")));
        }

        // TODO: Handvol onnodige velden (zoals startDateFirstLoanOfJohn,
        // endDateFirstLoanOfJohn), deze horen eerder in een test methode waar ze
        // gebruikt worden, tenzij ze enorm vaak hergebruikt worden, in which case make
        // sure you reset these in @BeforeEach)
        String startDateFirstLoanOfJohn = LocalDate.now().minusDays(2).toString();
        String endDateFirstLoanOfJohn = LocalDate.now().minusDays(1).toString();

        String startDateSecondLoanOfJohn = LocalDate.now().toString();

        @Test
        public void givenUsers_whenGetUsers_thenUsersAreReturned() {
                // TODO: Use text blocks (triple quotes) instead of many mini-strings and
                // concatenation (+) and \n and \"
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
                webTestClient.get().uri("/users/age/0/101").exchange().expectStatus().isOk().expectBody()
                                .json(
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
        public void givenUserEmailOfJohnDoe_whenGetLoansByEmail_thenCorrectLoansForEmailAreReturned() {
                // TODO: Voor al deze tests, maak gewoonte om van chained-methoden nieuwe regels
                // te maken waar logisch:
                // Voorbeeld: webTestClient.get().uri("/users/age/0/101").exchange()
                // .expectStatus().isOk()
                // .expectBody().json(
                webTestClient.get().uri("/users/john.doe@ucll.be/loans").exchange().expectStatus().isOk().expectBody()
                                .json(
                                                "[\n" +
                                                                "  {\n" +
                                                                "    \"startDate\": \"" + startDateFirstLoanOfJohn
                                                                + "\",\n" +
                                                                "    \"endDate\": \"" + endDateFirstLoanOfJohn + "\",\n"
                                                                +
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
                                                                "    \"startDate\": \"" + startDateSecondLoanOfJohn
                                                                + "\",\n" +
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

        @Test // TODO: Wees consistent in naamgeving (elders heb je dat creatingUser genoemd,
              // doe dat hier ook)
        @DirtiesContext
        public void givenNoUserWithEmailFromPeterGriffin_whenPostingNewUser_thenNewUserIsPosted() {
                webTestClient.post().uri("/users")
                                .header("Content-Type", "application/json")
                                .bodyValue(
                                                "{\n" +
                                                                "  \"name\": \"Peter Griffin\",\n" +
                                                                "  \"age\": 22,\n" +
                                                                "  \"email\": \"peter.griffin@ucll.be\",\n" +
                                                                "  \"password\": \"peter1234\"\n" +
                                                                "}")
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody().json(
                                                "{\n" +
                                                                "    \"name\": \"Peter Griffin\",\n" +
                                                                "    \"age\": 22,\n" +
                                                                "    \"email\": \"peter.griffin@ucll.be\",\n" +
                                                                "    \"password\": \"peter1234\"\n" +
                                                                "  }\n");

                assertTrue(userRepository.userExists("peter.griffin@ucll.be"));
        }

        @Test
        @DirtiesContext
        public void givenUserJohnDoe_whenChangingUserInfoByEmail_thenUserInfoIsExceptForEmailIsChanged() {
                webTestClient.put().uri("/users/john.doe@ucll.be").header("Content-Type", "application/json")
                                .bodyValue(
                                                "  {\n" +
                                                                "    \"name\": \"John Griffin\",\n" +
                                                                "    \"age\": 25,\n" +
                                                                "    \"email\": \"john.doe@ucll.be\",\n" +
                                                                "    \"password\": \"ilovedogs1234\"\n" +
                                                                "  }\n")
                                .exchange()
                                .expectStatus()
                                .isOk()
                                .expectBody()
                                .json(
                                                "  {\n" +
                                                                "    \"name\": \"John Griffin\",\n" +
                                                                "    \"age\": 25,\n" +
                                                                "    \"email\": \"john.doe@ucll.be\",\n" +
                                                                "    \"password\": \"ilovedogs1234\"\n" +
                                                                "  }\n");

        }

        @Test
        @DirtiesContext
        public void givenUserJaneToe_whenDeletingUserByEmail_thenUserIsCorrectlyDeleted() {
                webTestClient.delete().uri("/users/jane.toe@ucll.be").exchange().expectStatus().isOk()
                                .expectBody(String.class)
                                .isEqualTo("User successfully deleted.");
                // TODO: assertTrue(!foo) --> assertFalse(foo)
                assertTrue(!userRepository.userExists("jane.toe@ucll.be"));
        }
}