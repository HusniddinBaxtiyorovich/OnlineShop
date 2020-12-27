package uz.personal.enums;

public interface Permissions {

    /**
     * USER
     */
    String USER = "USER";
    String USER_READ = "USER_READ";
    String USER_CREATE = "USER_CREATE";
    String USER_UPDATE = "USER_UPDATE";
    String USER_DELETE = "USER_DELETE";
    String USER_ATTACH_ROLE = "USER_ATTACH_ROLE";
    String USER_CHANGE_PASSWORD= "USER_CHANGE_PASSWORD";

    /**
     * ROLE
     */
    String ROLE = "ROLE";
    String ROLE_READ = "ROLE_READ";
    String ROLE_CREATE = "ROLE_CREATE";
    String ROLE_UPDATE = "ROLE_UPDATE";
    String ROLE_DELETE = "ROLE_DELETE";
    String ROLE_ATTACH_PERMISSION = "ROLE_ATTACH_PERMISSION";

    /**
     * PERMISSION
     */
    String PERMISSION = "PERMISSION";
    String PERMISSION_READ = "PERMISSION_READ";
    String PERMISSION_CREATE = "PERMISSION_CREATE";
    String PERMISSION_UPDATE = "PERMISSION_UPDATE";
    String PERMISSION_DELETE = "PERMISSION_DELETE";
}
