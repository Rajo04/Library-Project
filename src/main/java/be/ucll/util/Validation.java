package be.ucll.util;

import java.time.LocalDate;

import be.ucll.model.DomainException;

public interface Validation {
    static void throwDomainException(String message) {
        throw new DomainException(message);
    }

    static void validateNonEmptyString(String value, String message) {
        if (value == null || value.isBlank()) {
            throwDomainException(message);
        }
    }

    static void validateNegativeNumber(int number, String message) {
        if (number < 0) {
            throwDomainException(message);
        }
    }

    static void validatePublicationYear(int year, String message) {
        if (year > LocalDate.now().getYear()) {
            throwDomainException(message);
        }
    }

    static void validateAge(int age, String message) {
        if (age < 0 || age > 101) {
            throwDomainException(message);
        }
    }

    static void validateEmail(String email, String message) {
        if (!email.contains("@") || !email.contains(".")) {
            throwDomainException(message);
        }
    }

    static void validatePassword(String password, String message) {
        if (password.length() < 8) {
            throwDomainException(message);
        }
    }

    static void validateIsbn(String isbn, String message) {
        if (isbn == null || isbn.isBlank()) {
            throwDomainException(message);
        }
        isbn = isbn.replaceAll("-", "");
        if (isbn.length() != 13) {
            throwDomainException(message);
        }
    }

    static void validateIssn(String issn, String message) {
        if (issn == null || issn.isBlank()) {
            throwDomainException(message);
        }
        issn = issn.replaceAll("-", "");
        if (issn.length() != 8) {
            throwDomainException(message);
        }
    }
}
