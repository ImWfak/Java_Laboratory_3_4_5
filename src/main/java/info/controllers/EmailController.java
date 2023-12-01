package info.controllers;

import info.entities.EmailData;
import info.services.EmailService;
import info.services.StudentInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/email")
@Controller
public class EmailController {
    private final EmailService emailService;
    private final StudentInfoService studentInfoService;
    @GetMapping("/send")
    public String sendEmail(Model model, EmailData email) {
        String filepath = "applicants.txt";
        //emailService.writeApplicantAndStudentInfoToTXT(studentInfoService.findAll(), filepath);
        emailService.sendMailWithAttachment(
                email.getToEmail(),
                email.getSubject(),
                email.getText(),
                filepath
        );
        model.addAttribute("emailMessage", "Email has been sent");
        return "redirect/:applicantsTable";
    }
}
