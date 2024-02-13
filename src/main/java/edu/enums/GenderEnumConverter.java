package edu.enums;

import jakarta.persistence.AttributeConverter;

public class GenderEnumConverter implements AttributeConverter<GenderEnum, String> {
    @Override
    public String convertToDatabaseColumn(GenderEnum gender) {
        return gender.toString();
    }
    @Override
    public GenderEnum convertToEntityAttribute(String gender) {
        return GenderEnum.fromString(gender);
    }
}
