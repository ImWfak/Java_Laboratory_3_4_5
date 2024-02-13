package edu.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.enums.GenderEnum;
import edu.enums.GenderEnumConverter;
import edu.regexes.ApplicantRegexes;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "applicants")
public class ApplicantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "applicant", cascade = {CascadeType.REMOVE})
    @JsonIgnore
    private StudentEntity student;

    @Column(name = "code", nullable = false, unique = true)
    @Pattern(regexp = ApplicantRegexes.CODE_REGEX)
    private String code;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "birthday", nullable = false)
    @DateTimeFormat(pattern = ApplicantRegexes.DATE_REGEX)
    private LocalDate birthday;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    @Convert(converter = GenderEnumConverter.class)
    private GenderEnum gender;

    @Column(name = "rating", nullable = false)
    @DecimalMin(value = ApplicantRegexes.MIN_RATING_REGEX, inclusive = false)
    @DecimalMax(value = ApplicantRegexes.MAX_RATING_REGEX, inclusive = false)
    private Float rating;

    public ApplicantEntity(
            String code,
            String surname,
            String name,
            String lastname,
            LocalDate birthday,
            GenderEnum gender,
            Float rating
    ) {
        id = null;
        student = null;
        this.code = code;
        this.surname = surname;
        this.name = name;
        this.lastname = lastname;
        this.birthday = birthday;
        this.gender = gender;
        this.rating = rating;
    }
}
