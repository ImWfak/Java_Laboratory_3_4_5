package edu.exceptions.applicantExceptions;

public class WrongApplicantRatingException extends IllegalArgumentException {
    public WrongApplicantRatingException(String message) {
        super(message);
    }
}
