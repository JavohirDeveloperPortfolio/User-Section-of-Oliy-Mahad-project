package uz.oliymahad.userservice.model.enums;

public enum PermissionEnum {
    COURSE_CREATE("course:create"),
    COURSE_READ("course:read"),
    COURSE_UPDATE("course:update"),
    COURSE_DELETE("course:delete"),

    TEACHER_CREATE("teacher:create"),
    TEACHER_READ("teacher:read"),
    TEACHER_UPDATE("teacher:update"),
    TEACHER_DELETE("teacher:delete"),
    ;


    private final String permission;

    PermissionEnum(String permission) {
        this.permission = permission;
    }
    public String getPermission() {
        return permission;
    }

}
