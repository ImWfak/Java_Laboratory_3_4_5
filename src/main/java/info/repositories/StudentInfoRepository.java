package info.repositories;

import info.entities.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentInfoRepository extends JpaRepository<StudentInfo, Long> {
    Optional<StudentInfo> findByApplicant_Id(Long applicant_id);
    void deleteByApplicant_Id(Long applicant_id);
}
