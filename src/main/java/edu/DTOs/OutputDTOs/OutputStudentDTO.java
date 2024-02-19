package edu.DTOs.OutputDTOs;

import edu.entities.StudentEntity;
import edu.exceptions.applicantExceptions.ApplicantDoesNotExistException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutputStudentDTO {
    private Long id;
    private Long applicant_id;
    private Boolean contract;
    private Boolean scholarship;
    private String email;

    private static Boolean recursive = false;

    public OutputStudentDTO(StudentEntity student)
            throws ApplicantDoesNotExistException {
        if (student.getApplicant() == null) {
            throw new ApplicantDoesNotExistException("Applicant does not exist");
        }
        id = student.getId();
        applicant_id = student.getApplicant().getId();
        contract = student.getContract();
        scholarship = student.getScholarship();
        email = student.getEmail();
    }
}
