package edu.services;

import edu.DTOs.InputDTOs.InputStudentDTO;
import edu.DTOs.OutputDTOs.OutputStudentDTO;
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

    public OutputStudentDTO save(InputStudentDTO inputStudent)
            throws ApplicantDoesNotExistException,
            WrongStudentEmailException,
            StudentEmailExistException {
        studentRepository.findByEmail(inputStudent.getEmail()).ifPresent(
                foundedStudent -> {
                    throw new StudentEmailExistException(
                            "Student with this email already exist, inputted email: " + inputStudent.getEmail()
                    );
                }
        );
        StudentEntity savedStudent = studentRepository.save(inputStudent.convertToStudentEntity(applicantRepository));
        return new OutputStudentDTO(savedStudent);
    }

    public OutputStudentDTO findById(Long id)
            throws StudentDoesNotExistException,
            ApplicantDoesNotExistException {
        StudentEntity foundedStudent = studentRepository.findById(id).orElseThrow(
                () -> new StudentDoesNotExistException(
                        "Student with this id does not exist, inputted id: " + id
                )
        );
        return new OutputStudentDTO(foundedStudent);
    }

    public OutputStudentDTO findByApplicant_Id(Long applicant_id)
            throws ApplicantDoesNotExistException,
            StudentDoesNotExistException {
        applicantRepository.findById(applicant_id).orElseThrow(
                () -> new ApplicantDoesNotExistException(
                        "Applicant with this id does not exist, inputted id: " + applicant_id
                )
        );
        StudentEntity foundedStudent = studentRepository.findByApplicant_Id(applicant_id).orElseThrow(
                () -> new StudentDoesNotExistException(
                        "Student with this applicant id does not exist, inputted applicant id: " + applicant_id
                )
        );
        return new OutputStudentDTO(foundedStudent);
    }

    public OutputStudentDTO findByEmail(String email)
            throws ApplicantDoesNotExistException,
            WrongStudentEmailException,
            StudentDoesNotExistException {
        if (!Pattern.compile(StudentRegexes.EMAIL_REGEX).matcher(email).matches()) {
            throw new WrongStudentEmailException(
                    "Wrong student email!\n"
                    + "email regex: '" + ApplicantRegexes.CODE_REGEX + "'\n"
                    + "inputted email: " + email
            );
        }
        StudentEntity foundedStudent = studentRepository.findByEmail(email).orElseThrow(
                () -> new StudentDoesNotExistException(
                        "Student with this email does not exist, inputted email: " + email
                )
        );
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

    public OutputStudentDTO updateById(Long id, InputStudentDTO inputApplicantDTO)
            throws ApplicantDoesNotExistException,
            WrongStudentEmailException,
            StudentDoesNotExistException,
            StudentEmailExistException {
        StudentEntity updatableStudent = studentRepository.findById(id).orElseThrow(
                () -> new StudentDoesNotExistException(
                        "Student with this id does not exist, inputted id: " + id
                )
        );
        if (!Objects.equals(inputApplicantDTO.getEmail(), updatableStudent.getEmail())
                && studentRepository.findByEmail(inputApplicantDTO.getEmail()).orElse(null) != null) {
            throw new StudentEmailExistException(
                    "Student with this email already exist, inputted email: " + inputApplicantDTO.getEmail()
            );
        }
        StudentEntity inputStudentEntity = inputApplicantDTO.convertToStudentEntity(applicantRepository);
        updatableStudent.setApplicant(inputStudentEntity.getApplicant());
        updatableStudent.setContract(inputStudentEntity.getContract());
        updatableStudent.setScholarship(inputStudentEntity.getScholarship());
        updatableStudent.setEmail(inputStudentEntity.getEmail());
        StudentEntity savedStudent = studentRepository.save(updatableStudent);
        return new OutputStudentDTO(savedStudent);
    }

    public OutputStudentDTO deleteById(Long id)
            throws StudentDoesNotExistException,
            ApplicantDoesNotExistException {
        StudentEntity foundedStudent = studentRepository.findById(id).orElseThrow(
                () -> new StudentDoesNotExistException(
                        "Student with this id does not exist, inputted id: " + id
                )
        );
        studentRepository.deleteById(id);
        return new OutputStudentDTO(foundedStudent);
    }
}
