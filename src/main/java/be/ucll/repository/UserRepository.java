package be.ucll.repository;

import be.ucll.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Overbodig, JpaRepository erft over van Repository, deze zorgt al voor een
            // @Repository-achtige registratie
public interface UserRepository extends JpaRepository<User, Long> {
    // List<User> allUsers(); //crud repository findAll method already does this

    List<User> findByAgeGreaterThan(int age);

    List<User> findByAgeBetween(int min, int max);

    List<User> findByName(String name);

    boolean existByEmail(String email);

    User findByEmail(String email);

    // User addUser(User user); // crud repository save method already does this

    // User updateUserByEmail(String email, User user); // crud repository save
    // method already does this

    // void deleteUserByEmail(String email); // crud repository deletebyid method
    // already does this
}
