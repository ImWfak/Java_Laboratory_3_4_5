package info.services;

import info.entities.StudentInfo;
import info.repositories.StudentInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentInfoService {
    private final StudentInfoRepository studentInfoRepository;
    public void save(StudentInfo studentInfo) {
        studentInfoRepository.save(studentInfo);
    }
    public StudentInfo findById(Long id) {
        return studentInfoRepository.findById(id).orElse(null);
    }
    public StudentInfo findByApplicantId(Long applicantId) {
        return studentInfoRepository.findByApplicant_Id(applicantId).orElse(null);
    }
    public List<StudentInfo> findAll() {
        return studentInfoRepository.findAll();
    }
    public void updateById(Long id, StudentInfo studentInfo) {
        StudentInfo updatableStudentInfo = this.findById(id);
        updatableStudentInfo.setApplicant(    studentInfo.getApplicant()    );
        updatableStudentInfo.setContract(     studentInfo.getContract()     );
        updatableStudentInfo.setScholarship(  studentInfo.getScholarship()  );
        updatableStudentInfo.setEmail(        studentInfo.getEmail()        );
        save(updatableStudentInfo);
    }
    public void updateByApplicantId(Long applicantId, StudentInfo studentInfo) {
        StudentInfo updatableStudentInfo = this.findByApplicantId(applicantId);
        updatableStudentInfo.setApplicant(    studentInfo.getApplicant()    );
        updatableStudentInfo.setContract(     studentInfo.getContract()     );
        updatableStudentInfo.setScholarship(  studentInfo.getScholarship()  );
        updatableStudentInfo.setEmail(        studentInfo.getEmail()        );
        save(updatableStudentInfo);
    }
    public void deleteById(Long id) {
        studentInfoRepository.deleteById(id);
    }
    public void deleteByApplicantId(Long applicantId) {
        studentInfoRepository.deleteByApplicant_Id(applicantId);
    }
}
