package edu.exceptions.applicantExceptions;

public class ApplicantCodeExistException extends IllegalArgumentException {
    public ApplicantCodeExistException(String message) {
        super(message);
    }
}
