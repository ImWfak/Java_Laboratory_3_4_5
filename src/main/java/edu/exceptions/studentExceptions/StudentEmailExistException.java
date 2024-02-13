package edu.exceptions.studentExceptions;

public class StudentEmailExistException extends IllegalArgumentException {
    public StudentEmailExistException(String message) {
        super(message);
    }
}
