package be.ucll.model;

public class ServiceException extends RuntimeException {
    private String field;

    public ServiceException(String field, String message) {
        super(message);
        this.field = field;
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException() {
        super();
    }

    public String getField() {
        return field;
    }
}
