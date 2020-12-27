package uz.personal.enums;

public enum UserType {

    ADMIN("ADMIN"),
    CLIENT("CLIENT");

    public String code;

    UserType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
