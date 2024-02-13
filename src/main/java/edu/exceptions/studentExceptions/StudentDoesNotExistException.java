package edu.exceptions.studentExceptions;

public class StudentDoesNotExistException extends Exception {
    public StudentDoesNotExistException(String message) {
        super(message);
    }
}
