package info.services;

import info.entities.Applicant;
import info.repositories.ApplicantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ApplicantService {
    private final ApplicantRepository applicantRepository;
    public void save(Applicant applicant) {
        applicantRepository.save(applicant);
    }
    public Applicant findById(Long id) {
        return applicantRepository.findById(id).orElse(null);
    }
    public List<Applicant> findAll() {
        return applicantRepository.findAll();
    }
    public void updateById(Long id, Applicant applicant) {
        Applicant updatebleApplicant = findById(id);
        updatebleApplicant.setCode(       applicant.getCode()       );
        updatebleApplicant.setSurname(    applicant.getSurname()    );
        updatebleApplicant.setName(       applicant.getName()       );
        updatebleApplicant.setLastname(   applicant.getLastname()   );
        updatebleApplicant.setBirthday(   applicant.getBirthday()   );
        updatebleApplicant.setGender(     applicant.getGender()     );
        updatebleApplicant.setRating(     applicant.getRating()     );
        applicantRepository.save(updatebleApplicant);
    }
    public void deleteById(Long id) {
        if (applicantRepository.findById(id).isEmpty())
            return;
        applicantRepository.deleteById(id);
    }
}
