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
    public StudentInfo findByApplicant_Id(Long applicant_Id) {
        return studentInfoRepository.findByApplicant_Id(applicant_Id).orElse(null);
    }
    public List<StudentInfo> findAll() {
        return studentInfoRepository.findAll();
    }
    public void updateById(Long id, StudentInfo studentInfo) {
        StudentInfo updatableStudentInfo = findById(id);
        if (updatableStudentInfo == null || studentInfo == null)
            return;
        updatableStudentInfo.setApplicant(    studentInfo.getApplicant()    );
        updatableStudentInfo.setContract(     studentInfo.getContract()     );
        updatableStudentInfo.setScholarship(  studentInfo.getScholarship()  );
        updatableStudentInfo.setEmail(        studentInfo.getEmail()        );
        save(updatableStudentInfo);
    }
    public void updateByApplicant_Id(Long applicant_Id, StudentInfo studentInfo) {
        StudentInfo updatableStudentInfo = findByApplicant_Id(applicant_Id);
        if (updatableStudentInfo == null || studentInfo == null)
            return;
        updatableStudentInfo.setApplicant(    studentInfo.getApplicant()    );
        updatableStudentInfo.setContract(     studentInfo.getContract()     );
        updatableStudentInfo.setScholarship(  studentInfo.getScholarship()  );
        updatableStudentInfo.setEmail(        studentInfo.getEmail()        );
        save(updatableStudentInfo);
    }
    public void deleteById(Long id) {
        studentInfoRepository.deleteById(id);
    }
    public void deleteByApplicant_Id(Long applicant_Id) {
        studentInfoRepository.deleteByApplicant_Id(applicant_Id);
    }
}
