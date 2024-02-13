package edu.DTO;

import edu.entities.ApplicantEntity;
import edu.entities.StudentEntity;
import edu.exceptions.applicantExceptions.ApplicantDoesNotExistException;
import edu.repositories.ApplicantRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InputStudentDTO {
    private Long applicant_id;
    private Boolean contract;
    private Boolean scholarship;
    private String email;

    public StudentEntity convertToStudentEntity(ApplicantRepository applicantRepository)
            throws ApplicantDoesNotExistException {
        ApplicantEntity applicant = applicantRepository.findById(applicant_id).orElse(null);
        if (applicant == null) {
            throw new ApplicantDoesNotExistException(
                    "Applicant with this id does not exist, id:" + applicant_id
            );
        }
        return new StudentEntity(
                applicant,
                contract,
                scholarship,
                email
        );
    }
}
