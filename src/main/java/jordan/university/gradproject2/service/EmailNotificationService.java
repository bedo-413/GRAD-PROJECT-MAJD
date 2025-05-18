package jordan.university.gradproject2.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationService implements NotificationService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${application.name:Activity Form System}")
    private String applicationName;

    @Override
    public boolean sendNotification(String recipient, String subject, String templateName, Map<String, Object> templateData) {
        try {
            // Create a Thymeleaf context
            Context context = new Context();
            templateData.forEach(context::setVariable);

            // Process the template
            String content = templateEngine.process(templateName, context);

            // Create a MIME message
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            // Set email properties
            helper.setFrom(fromEmail, applicationName);
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(content, true); // true indicates HTML content

            // Send the email
            mailSender.send(mimeMessage);
            log.info("Email notification sent to {} with subject: {}", recipient, subject);
            return true;
        } catch (Exception e) {
            log.error("Failed to send email notification to {}", recipient, e);
            return false;
        }
    }

    @Override
    public boolean sendSimpleNotification(String recipient, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(recipient);
            message.setSubject(subject);
            message.setText(content);

            mailSender.send(message);
            log.info("Simple email notification sent to {} with subject: {}", recipient, subject);
            return true;
        } catch (Exception e) {
            log.error("Failed to send simple email notification to {}", recipient, e);
            return false;
        }
    }
}