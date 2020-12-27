package uz.personal.enums;

public enum GeneratedClassType {

    DTO("Dto"),
    CREATEDTO("CreateDto"),
    UPDATEDTO("UpdateDto"),
    CRITERIA("Criteria"),
    MAPPER("Mapper"),
    IREPOSITORY("Repository"),
    ISERVICE("Service"),
    REPOSITORY("Repository"),
    CONTROLLER("Controller"),
    SERVICE("Service");

    public String value;

    GeneratedClassType(String value) {
        this.value = value;
    }
}
