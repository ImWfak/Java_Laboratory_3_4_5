package info.entities;

import lombok.Data;

@Data
public class EmailData {
    private String toEmail;
    private String subject;
    private String text;
    private String typeOfFile;
}
