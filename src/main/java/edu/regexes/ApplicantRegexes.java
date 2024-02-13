package edu.regexes;

public record ApplicantRegexes() {
    public static final String CODE_REGEX = "KN\\d{2}-\\d{4}";
    public static final String DATE_REGEX = "dd-mm-yyyy";
    public static final String MIN_RATING_REGEX = "120.0";
    public static final String MAX_RATING_REGEX = "200.0";
}
