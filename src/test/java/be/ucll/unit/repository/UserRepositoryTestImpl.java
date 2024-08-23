package be.ucll.unit.repository;

import be.ucll.model.User;
import be.ucll.repository.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class UserRepositoryTestImpl implements UserRepository {

    public List<User> users;

    public UserRepositoryTestImpl() {
        users = new ArrayList<>(List.of(
                new User("John Doe", 56, "john.doe@ucll.be", "john1234"),
                new User("Jane Toe", 30, "jane.toe@ucll.be", "jane1234"),
                new User("Jack Doe", 5, "jack.doe@ucll.be", "jack1234"),
                new User("Sarah Doe", 4, "sarah.doe@ucll.be", "sarah1234")));
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public void delete(User userToDelete) {
        String emailOfUserToDelete = userToDelete.getEmail();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);

            if (user.getEmail().equals(emailOfUserToDelete)) {
                users.remove(user);
                break;
            }
        }
    }

    @Override
    public List<User> findByAgeGreaterThan(int age) {
        List<User> allAdultUsers = new ArrayList<>();
        for (User user : users) {
            if (user.getAge() > age) {
                allAdultUsers.add(user);
            }
        }
        return allAdultUsers;
    }

    @Override
    public List<User> findByAgeBetween(int min, int max) {
        List<User> usersWithinAgeRange = new ArrayList<>();
        for (User user : users) {
            if (min <= user.getAge() && user.getAge() <= max) {
                usersWithinAgeRange.add(user);
            }
        }
        return usersWithinAgeRange;
    }

    @Override
    public List<User> findByName(String name) {
        List<User> filteredByName = new ArrayList<>();
        for (User user : users) {
            if (user.getName().toLowerCase().contains(name.toLowerCase())) {
                filteredByName.add(user);
            }
        }
        return filteredByName;
    }

    @Override
    public boolean existsByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User findByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User save(User user){
        // Save is both "Create new" and "Update if existing"
        // So: check if exists, if yes, update
        if(existsByEmail(user.getEmail())){
            // 👇 These are largely pointless sets, because we already did this in the service layer
            User existingUser = findByEmail(user.getEmail());
            existingUser.setEmail(user.getEmail());
            existingUser.setName(user.getName());
            existingUser.setAge(user.getAge());
            existingUser.setPassword(user.getPassword());
            user = existingUser;
        } else { // if no, create new
            users.add(user);
        }
        return user;
    }

//    @Override
//    public User addUser(User user) {
//        users.add(user);
//        return user;
//    }
//
//    @Override
//    public User updateUserByEmail(String email, User user) {
//        User fetchedUser = findByEmail(email);
//        fetchedUser.setEmail(user.getEmail());
//        fetchedUser.setName(user.getName());
//        fetchedUser.setAge(user.getAge());
//        fetchedUser.setPassword(user.getPassword());
//        return fetchedUser;
//    }
//
//    @Override
//    public void deleteUserByEmail(String email) {
//        for (int i = 0; i < users.size(); i++) {
//            User user = users.get(i);
//
//            if (user.getEmail().equals(email)) {
//                users.remove(user);
//                break;
//            }
//        }
//    }

    public void resetRepositoryData() {
        users = new ArrayList<>(List.of(
                new User("John Doe", 56, "john.doe@ucll.be", "john1234"),
                new User("Jane Toe", 30, "jane.toe@ucll.be", "jane1234"),
                new User("Jack Doe", 5, "jack.doe@ucll.be", "jack1234"),
                new User("Sarah Doe", 4, "sarah.doe@ucll.be", "sarah1234")));
    }

    // 👇 Methods we're forced to implement because the UserRepository-interface demands it.
    // 👇 We don't need any of these methods below to make our tests succeed


    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<User> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends User> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<User> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public User getOne(Long aLong) {
        return null;
    }

    @Override
    public User getById(Long aLong) {
        return null;
    }

    @Override
    public User getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends User> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<User> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }
}
