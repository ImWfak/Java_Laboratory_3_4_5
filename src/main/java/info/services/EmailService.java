package info.services;

import info.entities.Applicant;
import info.entities.StudentInfo;
import info.repositories.EmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class EmailService implements EmailRepository {
    private final JavaMailSender javaMailSender;
    private void writeApplicantAndStudentInfoToTXT(List<StudentInfo> studentInfoList, String filepath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            for (StudentInfo studentInfo : studentInfoList) {
                String line = String.format(
                        "%s, %s, %s, %s, %s, %s, %s, %s, %s, %s",
                        studentInfo.getApplicant().getCode(),
                        studentInfo.getApplicant().getSurname(),
                        studentInfo.getApplicant().getName(),
                        studentInfo.getApplicant().getLastname(),
                        studentInfo.getApplicant().getBirthday(),
                        studentInfo.getApplicant().getGender(),
                        studentInfo.getApplicant().getRating(),
                        studentInfo.getContract(),
                        studentInfo.getScholarship(),
                        studentInfo.getEmail()
                );
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }
    private void writeApplicantAndStudentInfoToCSV(List<Applicant> applicantList, String filepath) {}
    @Override
    public void sendMailWithAttachment(String to, String subject, String text, String pathToAttachment) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("borusovaleks@gmail.com");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setText(text);
            mimeMessageHelper.setSubject(subject);
            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
            mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException messagingException) {
            messagingException.printStackTrace();
        }
    }
}
