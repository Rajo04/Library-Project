package be.ucll.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import be.ucll.model.DomainException;
import be.ucll.model.Publication;
import be.ucll.model.ServiceException;
import be.ucll.service.PublicationService;

@RestController
@RequestMapping("/publications")
public class PublicationRestController {
    private PublicationService publicationService;

    public PublicationRestController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @GetMapping
    public List<Publication> getAllPublications(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String type) {
        return publicationService.findPublicationsByTitleAndType(title, type);
    }

    @GetMapping("/stock/{availableCopies}")
    public List<Publication> getPublicationsByAvailableCopies(@PathVariable int availableCopies) {
        return publicationService.findPublicationsByAvailableCopies(availableCopies);
    }
}
