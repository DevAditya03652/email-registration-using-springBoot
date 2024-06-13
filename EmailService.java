package com.example.demo.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

// Marks this class as a Spring service component.
@Service

// Lombok annotation to generate a constructor with all class fields as parameters.
@AllArgsConstructor
public class EmailService implements EmailSender{

    // Logger for logging email sending errors
    private final static Logger LOGGER = LoggerFactory
            .getLogger(EmailService.class);

    // JavaMailSender instance for sending emails.
    private final JavaMailSender mailSender;

    // Constructor to initialize JavaMailSender.
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    // Overrides the send method to send an email asynchronously.
    @Override
    @Async
    public void send(String to, String email) {

        try{

            // Create a MimeMessage for the email.
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            // Helper for setting up the email details.
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            // Set the email content, recipient, subject, and sender.
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("hello@amigoscode.com");
            // Send the email.
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Log an error message if email sending fails.
            LOGGER.error("failed to send email", e);
            // Throw an IllegalStateException if email sending fails.
            throw new IllegalStateException("failed to send email");
        }
    }
}
