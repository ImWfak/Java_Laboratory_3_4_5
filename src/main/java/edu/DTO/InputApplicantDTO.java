package edu.DTO;

import edu.entities.ApplicantEntity;
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
public class InputApplicantDTO {
    private String code;
    private String surname;
    private String name;
    private String lastname;
    private LocalDate birthday;
    private GenderEnum gender;
    private Float rating;

    public ApplicantEntity convertToApplicantEntity() {
        return new ApplicantEntity(
                code,
                surname,
                name,
                lastname,
                birthday,
                gender,
                rating
        );
    }
}
