package edu.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.regexes.StudentRegexes;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "students")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "applicant_id", referencedColumnName = "id", nullable = false, unique = true)
    @JsonIgnore
    @OneToOne
    private ApplicantEntity applicant;

    @Column(name = "contract", nullable = false)
    private Boolean contract;

    @Column(name = "scholarship", nullable = false)
    private Boolean scholarship;

    @Column(name = "email", nullable = false, unique = true)
    @Pattern(regexp = StudentRegexes.EMAIL_REGEX)
    private String email;

    public StudentEntity(
            ApplicantEntity applicant,
            Boolean contract,
            Boolean scholarship,
            String email
    ) {
        if (contract) {
            scholarship = false;
        }
        id = null;
        this.applicant = applicant;
        this.contract = contract;
        this.scholarship = scholarship;
        this.email = email;
    }

    //todo postConstruct method ignores, after add @AllArgsConstructor in StudentEntity and ApplicantEntity, also remove temp if in constructor(row 37 - 51)
    @PostConstruct
    private void postConstruct() {
        if (contract) {
            scholarship = false;
        }
    }
}
