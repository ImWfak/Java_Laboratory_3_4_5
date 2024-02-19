package edu.DTOs.InputDTOs;

import edu.entities.ApplicantEntity;
import edu.entities.StudentEntity;
import edu.exceptions.applicantExceptions.ApplicantDoesNotExistException;
import edu.exceptions.studentExceptions.WrongStudentEmailException;
import edu.regexes.ApplicantRegexes;
import edu.regexes.StudentRegexes;
import edu.repositories.ApplicantRepository;
import lombok.Getter;
import lombok.Setter;

import java.util.regex.Pattern;

@Getter
@Setter
public class InputStudentDTO {
    private Long applicant_id;
    private Boolean contract;
    private Boolean scholarship;
    private String email;

    public StudentEntity convertToStudentEntity(ApplicantRepository applicantRepository)
            throws ApplicantDoesNotExistException,
            WrongStudentEmailException {
        ApplicantEntity foundedApplicant = applicantRepository.findById(applicant_id).orElseThrow(
                () -> new ApplicantDoesNotExistException(
                        "Applicant with this id does not exist, id:" + applicant_id
                )
        );
        if (!Pattern.compile(StudentRegexes.EMAIL_REGEX).matcher(email).matches()) {
            throw new WrongStudentEmailException(
                    "Wrong student email!\n"
                            + "email regex: '" + ApplicantRegexes.CODE_REGEX + "'\n"
                            + "inputted email: " + email
            );
        }
        return new StudentEntity(
                foundedApplicant,
                contract,
                scholarship,
                email
        );
    }
}
