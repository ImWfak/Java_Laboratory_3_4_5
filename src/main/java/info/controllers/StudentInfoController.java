package info.controllers;

import info.entities.Applicant;
import info.entities.StudentInfo;
import info.services.ApplicantService;
import info.services.StudentInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/crud/studentInfo")
@Controller
public class StudentInfoController {
    private final ApplicantService applicantService;
    private final StudentInfoService studentInfoService;
    @PostMapping("/create/{applicantId}")
    public String saveStudentInfo(Model model, @PathVariable("applicantId") Long applicantId, StudentInfo studentInfo) {
        Applicant applicant = applicantService.findById(applicantId);
        if (applicant == null) {
            model.addAttribute("errorMessage", "No such applicant");
            return "redirect:/";
        }
        studentInfo.setApplicant(applicant);
        studentInfoService.save(studentInfo);
        return "redirect:/applicantsTable";
    }
    @PatchMapping("updateByApplicantId/{applicantId}")
    public String updateStudentInfoById(Model model, @PathVariable("applicantId") Long applicantId, StudentInfo studentInfo) {
        Applicant applicant = applicantService.findById(applicantId);
        if (applicant == null) {
            model.addAttribute("errorMessage", "No such applicant");
            return "redirect:/error";
        }
        if (studentInfoService.findByApplicantId(applicantId) == null) {
            model.addAttribute("errorMessage", "No such student info");
            return "redirect:/error";
        }
        studentInfo.setApplicant(applicant);
        studentInfoService.updateByApplicantId(applicantId, studentInfo);
        return "redirect:/applicantsTable";
    }
    @DeleteMapping("deleteByApplicantId/{applicantId}")
    public String deleteByStudentInfo(Model model, @PathVariable("applicantId") Long applicantId) {
        if (applicantService.findById(applicantId) == null) {
            model.addAttribute("errorMessage", "No such applicant");
            return "redirect:/error";
        }
        if (studentInfoService.findByApplicantId(applicantId) == null) {
            model.addAttribute("errorMessage", "No such student info");
            return "redirect:/error";
        }
        studentInfoService.deleteByApplicantId(applicantId);
        return "redirect:/applicantsTable";
    }
}
