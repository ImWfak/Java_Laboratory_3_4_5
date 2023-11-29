package info.entities;

import info.entities.enums.GenderEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "applicants")
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(
            name = "code",
            unique = true,
            nullable = false
    )
    private String code;
    @Column(
            name = "surname",
            nullable = false
    )
    private String surname;
    @Column(
            name = "name",
            nullable = false
    )
    private String name;
    @Column(
            name = "lastname",
            nullable = false
    )
    private String lastname;
    @Column(
            name = "birthday",
            nullable = false
    )
    private LocalDate birthday;
    @Enumerated(EnumType.STRING)
    @Column(
            name = "gender",
            nullable = false
    )
    private GenderEnum gender;
    @Column(
            name = "rating",
            nullable = false
    )
    private Float rating;
}
