package edu.DTO;

import edu.entities.ApplicantEntity;
import edu.entities.StudentEntity;
import edu.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OutputApplicantDTO {
    private Long id;
    private StudentEntity student;
    private String code;
    private String surname;
    private String name;
    private String lastname;
    private LocalDate birthday;
    private GenderEnum gender;
    private Float rating;

    public OutputApplicantDTO(ApplicantEntity applicant) {
        id = applicant.getId();
        student = applicant.getStudent();
        code = applicant.getCode();
        surname = applicant.getSurname();
        name = applicant.getName();
        lastname = applicant.getLastname();
        birthday = applicant.getBirthday();
        gender = applicant.getGender();
        rating = applicant.getRating();
    }
}
