package info.controllers;

import info.entities.Applicant;
import info.entities.StudentInfo;
import info.services.ApplicantService;
import info.services.StudentInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/load")
@Controller
public class LoadController {
    private final ApplicantService applicantService;
    private final StudentInfoService studentInfoService;
    @GetMapping("/applicant/create")
    public String loadCreateSaveApplicant() {
        return "applicantCreate";
    }
    @GetMapping("/applicant/updateById/{id}")
    public String loadUpdateApplicantById(Model model, @PathVariable("id") Long id) {
        Applicant applicant = applicantService.findById(id);
        if (applicant == null) {
            model.addAttribute("errorMessage", "No such applicant");
            return "redirect:/error";
        }
        model.addAttribute("applicant", applicant);
        return "applicantUpdate";
    }
    @GetMapping("/studentInfo/create/{applicantId}")
    public String loadCreateStudentInfo(Model model, @PathVariable("applicantId") Long applicantId) {
        Applicant applicant = applicantService.findById(applicantId);
        if (applicant == null) {
            model.addAttribute("errorMessage", "No such applicant");
            return "redirect:/error";
        }
        return "studentInfoCreate";
    }
    @GetMapping("/studentInfo/updateByApplicantId/{applicantId}")
    public String loadStudentInfoUpdateByApplicantId(RedirectAttributes redirectAttributes, Model model, @PathVariable("applicantId") Long applicantId) {
        Applicant applicant = applicantService.findById(applicantId);
        if (applicant == null) {
            model.addAttribute("errorMessage", "No such applicant");
            return "redirect:/error";
        }
        StudentInfo studentInfo = studentInfoService.findByApplicantId(applicantId);
        if (studentInfo == null) {
            redirectAttributes.addAttribute("applicantId", applicantId);
            return "redirect:/load/studentInfo/create/{applicantId}";
        }
        model.addAttribute("studentInfo", studentInfo);
        return "studentInfoUpdate";
    }
    @GetMapping("/error")
    public String loadError() {
        return "error";
    }
    @GetMapping("/login")
    public String loadLogin() {
        return "login";
    }
    @GetMapping("/register/user")
    public String loadRegisterUser(Model model) {
        return "registerUser";
    }
    @GetMapping("/register/admin")
    public String loadRegisterAdmin() {
        return "registerAdmin";
    }
    @GetMapping("/applicantsTable")
    public String loadApplicantsTable(Model model) {
        model.addAttribute("applicantList", applicantService.findAll());
        return "applicantsTable";
    }
    @GetMapping("/studentInfoByApplicantId/{applicantId}")
    public String loadStudentInfoByApplicantId(RedirectAttributes redirectAttributes, Model model, @PathVariable("applicantId") Long applicantId) {
        Applicant applicant = applicantService.findById(applicantId);
        if (applicant == null) {
            model.addAttribute("errorMessage", "No such applicant");
            return "redirect:/error";
        }
        StudentInfo studentInfo = studentInfoService.findByApplicantId(applicantId);
        if (studentInfo == null) {
            redirectAttributes.addAttribute("applicantId", applicantId);
            return "redirect:/load/studentInfo/create/{applicantId}";
        }
        model.addAttribute("studentInfo", studentInfo);
        return "studentInfo";
    }
    @GetMapping("/sort/applicantsTable")
    public String loadSortedApplicantTable(Model model, @RequestParam("sortBy") String sortBy) {
        List<Applicant> applicantList = applicantService.findAll();
        switch (sortBy) {
            case "code" -> applicantList.sort(
                    (firstApplicant, secondApplicant) -> firstApplicant.getCode().compareToIgnoreCase(secondApplicant.getCode())
            );
            case "surname" -> applicantList.sort(
                    (firstApplicant, secondApplicant) -> firstApplicant.getSurname().compareToIgnoreCase(secondApplicant.getSurname())
            );
            case "name" -> applicantList.sort(
                    (firstApplicant, secondApplicant) -> firstApplicant.getName().compareToIgnoreCase(secondApplicant.getName())
            );
            case "lastname" -> applicantList.sort(
                    (firstApplicant, secondApplicant) -> firstApplicant.getLastname().compareToIgnoreCase(secondApplicant.getLastname())
            );
            case "birthday" -> applicantList.sort(
                    Comparator.comparing(Applicant::getBirthday)
            );
            case "gender" -> applicantList.sort(
                    (firstApplicant, secondApplicant) -> firstApplicant.getGender().toString().compareToIgnoreCase(secondApplicant.toString())
            );
            case "rating" -> applicantList.sort(
                    Comparator.comparingDouble(Applicant::getRating)
            );
            default -> throw new IllegalStateException("Unexpected value: " + sortBy);
        }
        model.addAttribute("applicantList", applicantList);
        return "applicantsTable";
    }
    @GetMapping("/search/applicant")
    public String loadFoundedApplicant(Model model, @RequestParam("searchBy") String searchBy, @RequestParam("searchValue") String searchValue) {
        List<Applicant> applicantList = applicantService.findAll();
        List<Applicant> foundedApplicantList = new ArrayList<>();
        switch(searchBy) {
            case "code" -> {
                for (Applicant applicant : applicantList)
                    if (applicant.getCode().contains(searchValue))
                        foundedApplicantList.add(applicant);
            }
            case "surname" -> {
                for (Applicant applicant : applicantList)
                    if (applicant.getSurname().contains(searchValue))
                        foundedApplicantList.add(applicant);
            }
            case "name" -> {
                for (Applicant applicant : applicantList)
                    if (applicant.getName().contains(searchValue))
                        foundedApplicantList.add(applicant);
            }
            case "lastname" -> {
                for (Applicant applicant : applicantList)
                    if (applicant.getLastname().contains(searchValue))
                        foundedApplicantList.add(applicant);
            }
            case "gender" -> {
                for (Applicant applicant : applicantList)
                    if (applicant.getGender().toString().contains(searchValue))
                        foundedApplicantList.add(applicant);
            }
            case "birthday" -> {
                for (Applicant applicant : applicantList)
                    if (applicant.getBirthday().toString().contains(searchValue))
                        foundedApplicantList.add(applicant);
            }
            case "rating" -> {
                for (Applicant applicant : applicantList)
                    if (applicant.getRating().toString().contains(searchValue))
                        foundedApplicantList.add(applicant);
            }
            default -> throw new IllegalStateException("Unexpected value: " + searchValue);
        }
        model.addAttribute("applicantList", foundedApplicantList);
        return "applicantsTable";
    }
}
