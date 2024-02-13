package edu.exceptions.applicantExceptions;

public class WrongApplicantCodeException extends IllegalArgumentException {
    public WrongApplicantCodeException(String message) {
        super(message);
    }
}
