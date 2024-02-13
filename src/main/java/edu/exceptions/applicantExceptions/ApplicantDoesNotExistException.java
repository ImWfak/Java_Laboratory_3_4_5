package edu.exceptions.applicantExceptions;

public class ApplicantDoesNotExistException extends Exception {
    public ApplicantDoesNotExistException(String message) {
        super(message);
    }
}
