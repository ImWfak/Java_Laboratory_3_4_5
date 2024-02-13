package edu.enums;

import jakarta.persistence.AttributeConverter;

public class RoleEnumConverter implements AttributeConverter<RoleEnum, String> {
    @Override
    public String convertToDatabaseColumn(RoleEnum role) {
        return role.toString();
    }
    @Override
    public RoleEnum convertToEntityAttribute(String role) {
        return RoleEnum.fromString(role);
    }
}
