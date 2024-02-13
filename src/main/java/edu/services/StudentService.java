package edu.services;

import edu.DTO.InputStudentDTO;
import edu.DTO.OutputStudentDTO;
import edu.entities.StudentEntity;
import edu.exceptions.applicantExceptions.ApplicantDoesNotExistException;
import edu.exceptions.studentExceptions.StudentDoesNotExistException;
import edu.exceptions.studentExceptions.StudentEmailExistException;
import edu.exceptions.studentExceptions.WrongStudentEmailException;
import edu.regexes.ApplicantRegexes;
import edu.regexes.StudentRegexes;
import edu.repositories.ApplicantRepository;
import edu.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@AllArgsConstructor
@Service
public class StudentService {
    private ApplicantRepository applicantRepository;
    private StudentRepository studentRepository;

    private void validInputStudentDTO(InputStudentDTO inputStudentDTO)
            throws ApplicantDoesNotExistException,
            WrongStudentEmailException {
        if (applicantRepository.findById(inputStudentDTO.getApplicant_id()).orElse(null) == null) {
            throw new ApplicantDoesNotExistException(
                    "Applicant with this id does not exist, inputted id: " + inputStudentDTO.getApplicant_id()
            );
        }
        if (!Pattern.compile(StudentRegexes.EMAIL_REGEX).matcher(inputStudentDTO.getEmail()).matches()) {
            throw new WrongStudentEmailException(
                    "Wrong student email!\n"
                    + "email regex: '" + ApplicantRegexes.CODE_REGEX + "'\n"
                    + "inputted email: " + inputStudentDTO.getEmail()
            );
        }
    }

    public OutputStudentDTO save(InputStudentDTO inputStudentDTO)
            throws ApplicantDoesNotExistException,
            WrongStudentEmailException,
            StudentEmailExistException {
        validInputStudentDTO(inputStudentDTO);
        if (studentRepository.findByEmail(inputStudentDTO.getEmail()).orElse(null) != null) {
            throw new StudentEmailExistException(
                    "Student with this email already exist, inputted email: " + inputStudentDTO.getEmail()
            );
        }
        StudentEntity savedStudent = studentRepository.save(inputStudentDTO.convertToStudentEntity(applicantRepository));
        return new OutputStudentDTO(savedStudent);
    }

    public OutputStudentDTO findById(Long id)
            throws StudentDoesNotExistException,
            ApplicantDoesNotExistException {
        StudentEntity foundedStudent = studentRepository.findById(id).orElse(null);
        if (foundedStudent == null) {
            throw new StudentDoesNotExistException(
                    "Student with this id does not exist, inputted id: " + id
            );
        }
        return new OutputStudentDTO(foundedStudent);
    }

    public OutputStudentDTO findByApplicant_Id(Long applicant_id)
            throws ApplicantDoesNotExistException,
            StudentDoesNotExistException {
        if (applicantRepository.findById(applicant_id).orElse(null) == null) {
            throw new ApplicantDoesNotExistException(
                    "Applicant with this id does not exist, inputted id: " + applicant_id
            );
        }
        StudentEntity foundedStudent = studentRepository.findByApplicant_Id(applicant_id).orElse(null);
        if (foundedStudent == null) {
            throw new StudentDoesNotExistException(
                    "Student with this applicant id does not exist, inputted applicant id: " + applicant_id
            );
        }
        return new OutputStudentDTO(foundedStudent);
    }

    public OutputStudentDTO findByEmail(String email)
            throws WrongStudentEmailException,
            StudentDoesNotExistException,
            ApplicantDoesNotExistException {
        if (!Pattern.compile(StudentRegexes.EMAIL_REGEX).matcher(email).matches()) {
            throw new WrongStudentEmailException(
                    "Wrong student email!\n"
                    + "email regex: '" + ApplicantRegexes.CODE_REGEX + "'\n"
                    + "inputted email: " + email
            );
        }
        StudentEntity foundedStudent = studentRepository.findByEmail(email).orElse(null);
        if (foundedStudent == null) {
            throw new StudentDoesNotExistException(
                    "Student with this email does not exist, inputted email: " + email
            );
        }
        return new OutputStudentDTO(foundedStudent);
    }

    public List<OutputStudentDTO> findAll()
            throws ApplicantDoesNotExistException {
        List<OutputStudentDTO> outputStudentDTOList = new ArrayList<>();
        for (StudentEntity student : studentRepository.findAll()) {
            outputStudentDTOList.add(new OutputStudentDTO(student));
        }
        return outputStudentDTOList;
    }

    public OutputStudentDTO updateById(Long id, InputStudentDTO inputStudentDTO)
            throws StudentDoesNotExistException,
            ApplicantDoesNotExistException,
            WrongStudentEmailException,
            StudentEmailExistException {
        StudentEntity updatableStudent = studentRepository.findById(id).orElse(null);
        if (updatableStudent == null) {
            throw new StudentDoesNotExistException(
                    "Student with this id does not exist, inputted id: " + id
            );
        }
        validInputStudentDTO(inputStudentDTO);
        if (!Objects.equals(inputStudentDTO.getEmail(), updatableStudent.getEmail())
                && studentRepository.findByEmail(inputStudentDTO.getEmail()).orElse(null) != null) {
            throw new StudentEmailExistException(
                    "Student with this email already exist, inputted email: " + inputStudentDTO.getEmail()
            );
        }
        StudentEntity inputStudent = inputStudentDTO.convertToStudentEntity(applicantRepository);
        updatableStudent.setApplicant(inputStudent.getApplicant());
        updatableStudent.setContract(inputStudent.getContract());
        updatableStudent.setScholarship(inputStudent.getScholarship());
        updatableStudent.setEmail(inputStudent.getEmail());
        StudentEntity savedStudent = studentRepository.save(updatableStudent);
        return new OutputStudentDTO(savedStudent);
    }

    public OutputStudentDTO deleteById(Long id)
            throws StudentDoesNotExistException,
            ApplicantDoesNotExistException {
        StudentEntity foundedStudent = studentRepository.findById(id).orElse(null);
        if (foundedStudent == null) {
            throw new StudentDoesNotExistException(
                    "Student with this id does not exist, inputted id: " + id
            );
        }
        studentRepository.deleteById(id);
        return new OutputStudentDTO(foundedStudent);
    }
}
