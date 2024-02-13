package edu.exceptions.enumsExceptions;

public class UnknownValueForRoleEnum extends IllegalArgumentException {
    public UnknownValueForRoleEnum(String message) {
        super(message);
    }
}
