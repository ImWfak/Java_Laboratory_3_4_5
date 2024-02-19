package edu.enums;

import edu.exceptions.enumsExceptions.UnknownValueForRoleEnum;

import java.util.Arrays;

public enum RoleEnum {
    ADMIN("admin"),
    USER("user");
    private String role;
    RoleEnum(String role) {
        this.role = role;
    }
    @Override
    public String toString() {
        return role;
    }
    public static RoleEnum fromString(String role) {
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (roleEnum.role.equals(role)) {
                return roleEnum;
            }
        }
        throw new UnknownValueForRoleEnum(
                "This role does not exist!\n"
                        + "available roles: " + Arrays.toString(RoleEnum.values()) + "\n"
                        + "inputted role: " + role
        );
    }
}
