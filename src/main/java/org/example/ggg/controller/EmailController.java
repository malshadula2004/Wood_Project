package org.example.ggg.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailController {

    @FXML
    private TextField txtSenderEmail;

    @FXML
    private TextField txtRecipientEmail;

    @FXML
    private TextField txtSubject;

    @FXML
    private TextArea txtMessage;

    // මෙතන ඔයාගේ Gmail account එකේ app password එක දාන්න (Google Account > Security > App passwords)
    private final String senderPassword = "znuu odwu ioza srgx";

    @FXML
    private void sendEmail() {
        String senderEmail = txtSenderEmail.getText();
        String recipientEmail = txtRecipientEmail.getText();
        String subject = txtSubject.getText();
        String message = txtMessage.getText();

        if (senderEmail.isEmpty() || recipientEmail.isEmpty() || subject.isEmpty() || message.isEmpty()) {
            System.out.println("All fields are required!");
            return;
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(senderEmail));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            msg.setSubject(subject);
            msg.setText(message);

            Transport.send(msg);
            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }
}
