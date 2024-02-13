package edu.repositories;

import edu.entities.ApplicantEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicantRepository extends CrudRepository<ApplicantEntity, Long> {
    Optional<ApplicantEntity> findByCode(String code);
}
