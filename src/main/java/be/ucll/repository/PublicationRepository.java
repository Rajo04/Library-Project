package be.ucll.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import be.ucll.model.Book;
import be.ucll.model.Magazine;
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
                new Book("Franklin Brothers", "Josh Jefferson", "9783169484140", 2008, 10)));

        magazines = new ArrayList<>(List.of(
                new Magazine("The Guardian", "James Barnes", "12345678", 2010, 10),
                new Magazine("The New York Times", "Suzanne Collins", "12345671", 2014, 10),
                new Magazine("The Washington Post", "Josh Jefferson", "12345672", 2011, 10),
                new Magazine("The Wall Street Journal", "Josh Jefferson", "12345673", 2008, 10)));
    }

    public List<Publication> findByTitleAndType(String title, String type) {
        List<Publication> filteredByType = new ArrayList<>();
        if (type == null || type.equalsIgnoreCase("Book")) {
            filteredByType.addAll(books);
        }
        if (type == null || type.equalsIgnoreCase("Magazine")) {
            filteredByType.addAll(magazines);
        }

        if (title == null) {
            return filteredByType;
        } else {
            List<Publication> filteredByTitle = new ArrayList<>();
            for (Publication publication : filteredByType) {
                if (publication.getTitle().toLowerCase().contains(title.toLowerCase())) {
                    filteredByTitle.add(publication);
                }
            }
            return filteredByTitle;
        }
    }

    public List<Publication> filterByAvailableCopies(int availableCopies) {
        List<Publication> filteredByAvailableCopies = new ArrayList<>();
        for (Publication publication : books) {
            if (publication.getAvailableCopies() >= availableCopies) {
                filteredByAvailableCopies.add(publication);
            }
        }
        for (Publication publication : magazines) {
            if (publication.getAvailableCopies() >= availableCopies) {
                filteredByAvailableCopies.add(publication);
            }
        }
        return filteredByAvailableCopies;
    }

}
