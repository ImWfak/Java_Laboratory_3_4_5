package edu.DTOs.InputDTOs;

import edu.entities.ApplicantEntity;
import edu.enums.GenderEnum;
import edu.exceptions.applicantExceptions.WrongApplicantCodeException;
import edu.exceptions.applicantExceptions.WrongApplicantRatingException;
import edu.exceptions.enumsExceptions.UnknownValueForGenderEnum;
import edu.regexes.ApplicantRegexes;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

@Getter
@Setter
public class InputApplicantDTO {
    private String code;
    private String surname;
    private String name;
    private String lastname;
    private LocalDate birthday;
    private String gender;
    private Float rating;

    public ApplicantEntity convertToApplicantEntity()
            throws WrongApplicantCodeException,
            UnknownValueForGenderEnum,
            WrongApplicantRatingException {
        if (!Pattern.compile(ApplicantRegexes.CODE_REGEX).matcher(code).matches()) {
            throw new WrongApplicantCodeException(
                    "Wrong applicant code!\n"
                            + "code regex: '" + ApplicantRegexes.CODE_REGEX + "'\n"
                            + "inputted code: " + code
            );
        }
        if (Arrays.stream(GenderEnum.values()).noneMatch(genderEnum -> Objects.equals(genderEnum.toString(), gender))) {
            throw new UnknownValueForGenderEnum(
                    "This gender does not exist!\n"
                            + "available genders: " + Arrays.toString(GenderEnum.values()) + "\n"
                            + "inputted gender: " + gender
            );
        }
        if (rating < Float.parseFloat(ApplicantRegexes.MIN_RATING_REGEX)
                && rating > Float.parseFloat(ApplicantRegexes.MAX_RATING_REGEX)) {
            throw new WrongApplicantRatingException(
                    "Wrong applicant rating!\n"
                            + "minimal rating: " + ApplicantRegexes.MIN_RATING_REGEX + "\n"
                            + "maximal rating: " + ApplicantRegexes.MAX_RATING_REGEX + "\n"
                            + "inputted rating: " + rating
            );
        }
        return new ApplicantEntity(
                code,
                surname,
                name,
                lastname,
                birthday,
                GenderEnum.fromString(gender),
                rating
        );
    }
}
