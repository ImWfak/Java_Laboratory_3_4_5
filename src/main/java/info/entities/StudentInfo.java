package info.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "students_info")
public class StudentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(
            name = "applicant_id",
            unique = true,
            nullable = false
    )
    private Applicant applicant;
    @Column(
            name = "contract",
            nullable = false
    )
    private Boolean contract;
    @Column(
            name = "scholarship",
            nullable = false
    )
    private Boolean scholarship;
    @Column(
            name = "email",
            unique = true,
            nullable = false
    )
    private String email;
}
