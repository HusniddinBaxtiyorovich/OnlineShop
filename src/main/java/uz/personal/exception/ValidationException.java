package uz.personal.exception;

public class ValidationException extends GenericRuntimeException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, String key) {
        super(message, key);
    }
}
