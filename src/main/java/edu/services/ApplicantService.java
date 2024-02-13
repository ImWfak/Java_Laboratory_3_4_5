package edu.services;

import edu.DTO.InputApplicantDTO;
import edu.DTO.OutputApplicantDTO;
import edu.entities.ApplicantEntity;
import edu.enums.GenderEnum;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@AllArgsConstructor
@Service
public class ApplicantService {
    private ApplicantRepository applicantRepository;

    private void validateInputApplicantDTO(InputApplicantDTO inputApplicantDTO)
            throws WrongApplicantCodeException,
            UnknownValueForGenderEnum,
            WrongApplicantRatingException {
        if (!Pattern.compile(ApplicantRegexes.CODE_REGEX).matcher(inputApplicantDTO.getCode()).matches()) {
            throw new WrongApplicantCodeException(
                    "Wrong applicant code!\n"
                    + "code regex: '" + ApplicantRegexes.CODE_REGEX + "'\n"
                    + "inputted code: " + inputApplicantDTO.getCode()
            );
        }
        if (Arrays.stream(GenderEnum.values()).noneMatch(gender -> gender == inputApplicantDTO.getGender())) {
            throw new UnknownValueForGenderEnum(
                    "This gender does not exist!\n"
                    + "available genders: " + Arrays.toString(GenderEnum.values()) + "\n"
                    + "inputted gender: " + inputApplicantDTO.getGender()
            );
        }
        if (inputApplicantDTO.getRating() < Float.parseFloat(ApplicantRegexes.MIN_RATING_REGEX)
                && inputApplicantDTO.getRating() > Float.parseFloat(ApplicantRegexes.MAX_RATING_REGEX)) {
            throw new WrongApplicantRatingException(
                    "Wrong applicant rating!\n"
                    + "minimal rating: " + ApplicantRegexes.MIN_RATING_REGEX + "\n"
                    + "maximal rating: " + ApplicantRegexes.MAX_RATING_REGEX + "\n"
                    + "inputted rating: " + inputApplicantDTO.getRating()
            );
        }
    }

    public OutputApplicantDTO save(InputApplicantDTO inputApplicantDTO)
            throws ApplicantCodeExistException,
            WrongApplicantCodeException,
            UnknownValueForGenderEnum,
            WrongApplicantRatingException {
        validateInputApplicantDTO(inputApplicantDTO);
        if (applicantRepository.findByCode(inputApplicantDTO.getCode()).orElse(null) != null) {
            throw new ApplicantCodeExistException(
                    "Applicant with this code already exist, inputted code: " + inputApplicantDTO.getCode()
            );
        }
        ApplicantEntity saveApplicant = applicantRepository.save(inputApplicantDTO.convertToApplicantEntity());
        return new OutputApplicantDTO(saveApplicant);
    }

    public OutputApplicantDTO findById(Long id)
            throws ApplicantDoesNotExistException {
        ApplicantEntity foundedApplicant = applicantRepository.findById(id).orElse(null);
        if (foundedApplicant == null) {
            throw new ApplicantDoesNotExistException(
                    "Applicant with this id does not exist, inputted id: " + id
            );
        }
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
        ApplicantEntity foundedApplicant = applicantRepository.findByCode(code).orElse(null);
        if (foundedApplicant == null) {
            throw new ApplicantDoesNotExistException(
                    "Applicant with this code does not exist, inputted code: " + code
            );
        }
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
            throws ApplicantDoesNotExistException,
            ApplicantCodeExistException {
        ApplicantEntity updatableApplicant = applicantRepository.findById(id).orElse(null);
        if (updatableApplicant == null) {
            throw new ApplicantDoesNotExistException(
                    "Applicant with this id does not exist, inputted id: " + id
            );
        }
        validateInputApplicantDTO(inputApplicantDTO);
        if (!Objects.equals(inputApplicantDTO.getCode(), updatableApplicant.getCode())
                && applicantRepository.findByCode(inputApplicantDTO.getCode()).orElse(null) != null) {
            throw new ApplicantCodeExistException(
                    "Applicant with this code already exist, inputted code: " + inputApplicantDTO.getCode()
            );
        }
        ApplicantEntity inputApplicant = inputApplicantDTO.convertToApplicantEntity();
        updatableApplicant.setCode(inputApplicant.getCode());
        updatableApplicant.setSurname(inputApplicant.getSurname());
        updatableApplicant.setName(inputApplicant.getName());
        updatableApplicant.setLastname(inputApplicant.getLastname());
        updatableApplicant.setBirthday(inputApplicant.getBirthday());
        updatableApplicant.setGender(inputApplicant.getGender());
        updatableApplicant.setRating(inputApplicant.getRating());
        ApplicantEntity updatedApplicant = applicantRepository.save(updatableApplicant);
        return new OutputApplicantDTO(updatedApplicant);
    }

    public OutputApplicantDTO deleteById(Long id)
            throws ApplicantDoesNotExistException {
        ApplicantEntity applicant = applicantRepository.findById(id).orElse(null);
        if (applicant == null) {
            throw new ApplicantDoesNotExistException(
                    "Applicant with this id does not exist, inputted id: " + id
            );
        }
        applicantRepository.deleteById(id);
        return new OutputApplicantDTO(applicant);
    }
}
