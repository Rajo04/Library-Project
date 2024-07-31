package be.ucll.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import be.ucll.model.Book;
import be.ucll.model.Publication;


@Repository
public class PublicationRepository {
    public List<Publication> books;
    public List<Publication> magazines;

    public PublicationRepository() {
        books = new ArrayList<>(List.of(
            new Book("Vikings", "Arthur", "9783161484100", 2010, 10),
            new Book("Annabel", "James Barnes", "9783161484140", 2014, 10),
            new Book("The Hunger Games", "Suzanne collins", "9783161488140", 2011, 10),
            new Book("Franklin Brothers", "Josh Jefferson", "9783169484140", 2008, 10);
        
        magazines = new ArrayList<>() {

        }
        ));
    }

    public List<Publication> findByTitleAndType(String title, String type) {
        List<Publication> filteredByTitleOrType = new ArrayList<>();

        for (Publication publication : filteredByTitleOrType) {
            
        }
        return null;
    }

}
