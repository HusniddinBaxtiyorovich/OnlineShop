package uz.personal.enums;

public enum ErrorCodes {

    ERROR_MESSAGE_NOT_FOUND("ERROR_MESSAGE_NOT_FOUND", "Error message with code %s not found"),
    USER_NOT_FOUND_ID("USER_NOT_FOUND_ID", "User with provided id: %s not found"),
    USER_IS_DELETED("USER_IS_DELETED", "User with provided id: %s is deleted"),
    USER_IS_INACTIVE("USER_IS_INACTIVE", "User with provided id: %s is inactive"),
    USER_IS_BLOCKED("USER_IS_BLOCKED", "User with provided id: %s is blocked"),
    OBJECT_NOT_FOUND("OBJECT_NOT_FOUND", "%s not found"),
    OBJECT_NOT_FOUND_ID("OBJECT_NOT_FOUND_ID", "%s with provided id: %s not found"),
    OBJECT_IS_DELETED("OBJECT_IS_DELETED", "%s with provided id: %s is deleted"),
    OBJECT_IS_NULL("OBJECT_IS_NULL", "Provided object: %s is null"),
    OBJECT_NOT_NULL("OBJECT_NOT_NULL", "Provided object: %s is not null"),
    OBJECT_ID_REQUIRED("OBJECT_ID_REQUIRED", "%s id not provided"),
    OBJECT_GIVEN_FIELD_REQUIRED("OBJECT_GIVEN_FIELD_REQUIRED", "Provided field : %s of %s is required"),
    ACCESS_DENIED("ACCESS_DENIED", "You do not have access for %s"),
    PASSWORD_INCORRECT("PASSWORD_INCORRECT", "Password is incorrect"),
    OBJECT_SHOULD_BE("OBJECT_SHOULD_BE", "Provider object: %s should be %s");

    public String code;
    public String example;

    ErrorCodes(String code, String example) {
        this.code = code;
        this.example = example;
    }
}
