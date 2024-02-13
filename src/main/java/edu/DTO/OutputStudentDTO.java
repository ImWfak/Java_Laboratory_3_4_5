package edu.DTO;

import edu.entities.ApplicantEntity;
import edu.entities.StudentEntity;
import edu.exceptions.applicantExceptions.ApplicantDoesNotExistException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OutputStudentDTO {
    private Long id;
    private ApplicantEntity applicant;
    private Boolean contract;
    private Boolean scholarship;
    private String email;

    public OutputStudentDTO(StudentEntity student)
            throws ApplicantDoesNotExistException {
        if (student.getApplicant() == null) {
            throw new ApplicantDoesNotExistException("Applicant does not exist");
        }
        id = student.getId();
        applicant = student.getApplicant();
        contract = student.getContract();
        scholarship = student.getScholarship();
        email = student.getEmail();
    }
}
