package info.repositories;

public interface EmailRepository {
    void sendMailWithAttachment(String to, String subject, String text, String pathToAttachment);
}
