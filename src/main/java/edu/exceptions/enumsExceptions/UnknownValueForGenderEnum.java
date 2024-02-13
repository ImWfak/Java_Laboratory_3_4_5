package edu.exceptions.enumsExceptions;

public class UnknownValueForGenderEnum extends IllegalArgumentException {
    public UnknownValueForGenderEnum(String message) {
        super(message);
    }
}
