package edu.services;

import edu.DTOs.InputDTOs.InputApplicantDTO;
import edu.DTOs.OutputDTOs.OutputApplicantDTO;
import edu.entities.ApplicantEntity;
import edu.exceptions.applicantExceptions.ApplicantCodeExistException;
import edu.exceptions.applicantExceptions.ApplicantDoesNotExistException;
import edu.exceptions.applicantExceptions.WrongApplicantCodeException;
import edu.exceptions.applicantExceptions.WrongApplicantRatingException;
import edu.exceptions.enumsExceptions.UnknownValueForGenderEnum;
import edu.regexes.ApplicantRegexes;
import edu.repositories.ApplicantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@AllArgsConstructor
@Service
public class ApplicantService {
    private ApplicantRepository applicantRepository;

    public OutputApplicantDTO save(InputApplicantDTO inputApplicant)
            throws WrongApplicantCodeException,
            UnknownValueForGenderEnum,
            WrongApplicantRatingException,
            ApplicantCodeExistException {
        applicantRepository.findByCode(inputApplicant.getCode()).ifPresent(
                foundedApplicant -> {
                    throw new ApplicantCodeExistException(
                            "Applicant with this code already exist, inputted code: " + inputApplicant.getCode()
                    );
                }
        );
        ApplicantEntity savedApplicant = applicantRepository.save(inputApplicant.convertToApplicantEntity());
        return new OutputApplicantDTO(savedApplicant);
    }

    public OutputApplicantDTO findById(Long id)
            throws ApplicantDoesNotExistException {
        ApplicantEntity foundedApplicant = applicantRepository.findById(id).orElseThrow(
                () -> new ApplicantDoesNotExistException(
                        "Applicant with this id does not exist, inputted id: " + id
                )
        );
        return new OutputApplicantDTO(foundedApplicant);
    }

    public OutputApplicantDTO findByCode(String code)
            throws WrongApplicantCodeException,
            ApplicantDoesNotExistException {
        if (!Pattern.compile(ApplicantRegexes.CODE_REGEX).matcher(code).matches()) {
            throw new WrongApplicantCodeException(
                    "Wrong applicant code!\n"
                    + "code regex: '" + ApplicantRegexes.CODE_REGEX + "'\n"
                    + "inputted code: " + code
            );
        }
        ApplicantEntity foundedApplicant = applicantRepository.findByCode(code).orElseThrow(
                () -> new ApplicantDoesNotExistException(
                        "Applicant with this code does not exist, inputted code: " + code
                )
        );
        return new OutputApplicantDTO(foundedApplicant);
    }

    public List<OutputApplicantDTO> findAll() {
        List<OutputApplicantDTO> outputApplicantDTOList = new ArrayList<>();
        for (ApplicantEntity applicant : applicantRepository.findAll()) {
            outputApplicantDTOList.add(new OutputApplicantDTO(applicant));
        }
        return outputApplicantDTOList;
    }

    public OutputApplicantDTO updateById(Long id, InputApplicantDTO inputApplicantDTO)
            throws WrongApplicantCodeException,
            UnknownValueForGenderEnum,
            WrongApplicantRatingException,
            ApplicantDoesNotExistException,
            ApplicantCodeExistException {
        ApplicantEntity updatableApplicant = applicantRepository.findById(id).orElseThrow(
                () -> new ApplicantDoesNotExistException(
                        "Applicant with this id does not exist, inputted id: " + id
                )
        );
        if (!Objects.equals(inputApplicantDTO.getCode(), updatableApplicant.getCode())
                && applicantRepository.findByCode(inputApplicantDTO.getCode()).orElse(null) != null) {
            throw new ApplicantCodeExistException(
                    "Applicant with this code already exist, inputted code: " + inputApplicantDTO.getCode()
            );
        }
        ApplicantEntity inputApplicantEntity = inputApplicantDTO.convertToApplicantEntity();
        updatableApplicant.setCode(inputApplicantEntity.getCode());
        updatableApplicant.setSurname(inputApplicantEntity.getSurname());
        updatableApplicant.setName(inputApplicantEntity.getName());
        updatableApplicant.setLastname(inputApplicantEntity.getLastname());
        updatableApplicant.setBirthday(inputApplicantEntity.getBirthday());
        updatableApplicant.setGender(inputApplicantEntity.getGender());
        updatableApplicant.setRating(inputApplicantEntity.getRating());
        ApplicantEntity updatedApplicant = applicantRepository.save(updatableApplicant);
        return new OutputApplicantDTO(updatedApplicant);
    }

    public OutputApplicantDTO deleteById(Long id)
            throws ApplicantDoesNotExistException {
        ApplicantEntity deletableApplicant = applicantRepository.findById(id).orElseThrow(
                () -> new ApplicantDoesNotExistException(
                        "Applicant with this id does not exist, inputted id: " + id
                )
        );
        applicantRepository.deleteById(id);
        return new OutputApplicantDTO(deletableApplicant);
    }
}
