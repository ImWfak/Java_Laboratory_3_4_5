package info.controllers;

import info.entities.Applicant;
import info.services.ApplicantService;
import info.services.StudentInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("crud/applicant")
@Controller
public class ApplicantController {
    private final ApplicantService applicantService;
    private final StudentInfoService studentInfoService;
    @PostMapping("/create")
    public String saveApplicant(Applicant applicant) {
        applicantService.save(applicant);
        return "redirect:/load/applicantsTable";
    }
    @PostMapping("/updateById/{id}")
    public String updateApplicantById(Model model, @PathVariable("id") Long id, Applicant applicant) {
        if (applicantService.findById(id) == null) {
            model.addAttribute("errorMessage", "No such applicant");
            return "redirect:/error";
        }
        applicantService.updateById(id, applicant);
        return "redirect:/load/applicantsTable";
    }
    @PostMapping("/deleteById/{id}")
    public String deleteApplicantById(Model model, @PathVariable("id") Long id) {
        if (applicantService.findById(id) == null) {
            model.addAttribute("errorMessage", "No such applicant");
            return "redirect:/error";
        }
        if (studentInfoService.findByApplicantId(id) != null)
            studentInfoService.deleteByApplicantId(id);
        applicantService.deleteById(id);
        return "redirect:/load/applicantsTable";
    }
}
