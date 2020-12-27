package uz.personal.enums;

public enum LanguageType {

    UZ("UZBEK"),
    RU("RUSSIAN"),
    EN("ENGLISH");

    public String code;

    LanguageType(String code) {
        this.code = code;
    }
}
