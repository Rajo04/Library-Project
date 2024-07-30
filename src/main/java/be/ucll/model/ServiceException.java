package be.ucll.model;

public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException() {
        super();
    }
}
