package be.ucll.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import be.ucll.model.DomainException;
import be.ucll.model.ServiceException;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler({ ServiceException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleServiceException(ServiceException e) {
        return Map.of("ServiceException", e.getMessage());
    }

    @ExceptionHandler({ DomainException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleDomainException(DomainException e) {
        return Map.of("DomainException", e.getMessage());
    }
}
