package edu.DTOs.OutputDTOs;

import edu.entities.ApplicantEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OutputApplicantDTO {
    private Long id;
    private Long student_id;
    private String code;
    private String surname;
    private String name;
    private String lastname;
    private LocalDate birthday;
    private String gender;
    private Float rating;

    public OutputApplicantDTO(ApplicantEntity applicant) {
        id = applicant.getId();
        if (applicant.getStudent() != null) {
            student_id = applicant.getStudent().getId();
        }
        else {
            student_id = null;
        }
        code = applicant.getCode();
        surname = applicant.getSurname();
        name = applicant.getName();
        lastname = applicant.getLastname();
        birthday = applicant.getBirthday();
        gender = applicant.getGender().toString();
        rating = applicant.getRating();
    }
}
