package edu.enums;

import edu.exceptions.enumsExceptions.UnknownValueForGenderEnum;

import java.util.Arrays;

public enum GenderEnum {
    MALE("male"),
    FEMALE("female");
    private String gender;
    GenderEnum(String gender) {
        this.gender = gender;
    }
    @Override
    public String toString() {
        return gender;
    }
    public static GenderEnum fromString(String gender) {
        for (GenderEnum genderEnum : GenderEnum.values()) {
            if (genderEnum.gender.equals(gender)) {
                return genderEnum;
            }
        }
        throw new UnknownValueForGenderEnum(
                "This gender does not exist!\n"
                        + "available genders: " + Arrays.toString(GenderEnum.values()) + "\n"
                        + "inputted gender: " + gender
        );
    }
}
