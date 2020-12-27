package uz.personal.exception;

public class IdRequiredException extends GenericRuntimeException {

    public IdRequiredException(String message) {
        super(message);
    }

    public IdRequiredException(String message, String key) {
        super(message, key);
    }

    public IdRequiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdRequiredException(String message, Throwable cause, String key) {
        super(message, cause, key);
    }
}
