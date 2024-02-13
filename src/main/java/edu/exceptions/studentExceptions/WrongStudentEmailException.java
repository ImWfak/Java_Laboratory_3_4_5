package edu.exceptions.studentExceptions;

public class WrongStudentEmailException extends IllegalArgumentException {
    public WrongStudentEmailException(String message) {
        super(message);
    }
}
