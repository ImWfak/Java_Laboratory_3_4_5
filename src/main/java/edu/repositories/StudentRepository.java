package edu.repositories;

import edu.entities.StudentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<StudentEntity, Long> {
    Optional<StudentEntity> findByApplicant_Id(Long applicant_id);
    Optional<StudentEntity> findByEmail(String email);
}
