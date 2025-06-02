package jordan.university.gradproject2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.internet.MimeMessage;
import jordan.university.gradproject2.entity.ActivityFormEntity;
import jordan.university.gradproject2.enums.Status;
import jordan.university.gradproject2.model.ActivityForm;
import jordan.university.gradproject2.model.EmailNotification;
import jordan.university.gradproject2.model.User;
import jordan.university.gradproject2.repository.activity.ActivityFormJpaRepository;
import jordan.university.gradproject2.repository.email.EmailNotificationRepository;
import jordan.university.gradproject2.request.EmailNotificationRequest;
import jordan.university.gradproject2.resource.EmailNotificationResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationService implements NotificationService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final EmailNotificationRepository emailNotificationRepository;
    private final ActivityFormJpaRepository activityFormJpaRepository;
    private final ObjectMapper objectMapper;
    private final Environment environment;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${application.name:Activity Form System}")
    private String applicationName;

    @Override
    public EmailNotificationResource sendNotification(String recipient, String subject, String templateName, Map<String, Object> templateData) {
        return sendNotification(recipient, subject, templateName, templateData, null);
    }

    public EmailNotificationResource sendNotification(String recipient, String subject, String templateName, Map<String, Object> templateData, ActivityForm activityForm) {
        // Create email notification
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setRecipient(recipient);
        emailNotification.setSubject(subject);
        emailNotification.setTemplateName(templateName);
        emailNotification.setStatus("PENDING");
        emailNotification.setActivityForm(activityForm);

        try {
            // Serialize template data
            try {
                emailNotification.setTemplateData(objectMapper.writeValueAsString(templateData));
            } catch (JsonProcessingException e) {
                log.error("Failed to serialize template data", e);
                emailNotification.setTemplateData("{}");
            }

            // Save notification to database
            EmailNotification savedNotification = emailNotificationRepository.save(emailNotification);

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

            // Update email notification status
            savedNotification.setStatus("SENT");
            savedNotification.setSentAt(LocalDateTime.now());
            savedNotification = emailNotificationRepository.save(savedNotification);

            log.info("Email notification sent to {} with subject: {}", recipient, subject);
            return toResource(savedNotification);
        } catch (Exception e) {
            // Other unexpected exceptions
            emailNotification.setStatus("FAILED");
            EmailNotification savedNotification = emailNotificationRepository.save(emailNotification);

            log.error("Failed to send email notification to {}", recipient, e);
            return toResource(savedNotification);
        }
    }

    private void updateEmailStatus(String recipient, String subject, String status) {
        List<EmailNotification> notifications = emailNotificationRepository.findByRecipient(recipient);
        for (EmailNotification notification : notifications) {
            if (notification.getSubject().equals(subject) &&
                    (notification.getStatus().equals("PENDING") || notification.getStatus().equals("FAILED"))) {
                notification.setStatus(status);
                if ("SENT".equals(status)) {
                    notification.setSentAt(LocalDateTime.now());
                }
                emailNotificationRepository.save(notification);
                break;
            }
        }
    }

    @Override
    public EmailNotificationResource sendSimpleNotification(String recipient, String subject, String content) {
        return sendSimpleNotification(recipient, subject, content, null);
    }

    public EmailNotificationResource sendSimpleNotification(String recipient, String subject, String content, ActivityForm activityForm) {
        // Create email notification
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setRecipient(recipient);
        emailNotification.setSubject(subject);
        emailNotification.setTemplateName("simple");
        emailNotification.setTemplateData(content);
        emailNotification.setStatus("PENDING");
        emailNotification.setActivityForm(activityForm);

        try {
            // Save notification to database
            EmailNotification savedNotification = emailNotificationRepository.save(emailNotification);

            // Create and send simple message
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(recipient);
            message.setSubject(subject);
            message.setText(content);

            mailSender.send(message);

            // Update email notification status
            savedNotification.setStatus("SENT");
            savedNotification.setSentAt(LocalDateTime.now());
            savedNotification = emailNotificationRepository.save(savedNotification);

            log.info("Simple email notification sent to {} with subject: {}", recipient, subject);
            return toResource(savedNotification);
        } catch (Exception e) {
            // Other unexpected exceptions
            emailNotification.setStatus("FAILED");
            EmailNotification savedNotification = emailNotificationRepository.save(emailNotification);

            log.error("Failed to send simple email notification to {}", recipient, e);
            return toResource(savedNotification);
        }
    }

    /**
     * Create a new email notification from a request
     *
     * @param request The email notification request
     * @return The created email notification resource
     */
    public EmailNotificationResource createEmailNotification(EmailNotificationRequest request) {
        // Find the activity form
        ActivityFormEntity activityFormEntity = activityFormJpaRepository.findById(request.getActivityFormId())
                .orElseThrow(() -> new IllegalArgumentException("Activity form not found with ID: " + request.getActivityFormId()));

        // Create the email notification
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setRecipient(request.getRecipient());
        emailNotification.setSubject(request.getSubject());
        emailNotification.setTemplateName(request.getTemplateName());
        emailNotification.setTemplateData(request.getTemplateData());
        emailNotification.setStatus("PENDING");

        // Convert ActivityFormEntity to ActivityForm (this would typically be done by a mapper)
        ActivityForm activityForm = new ActivityForm();
        activityForm.setId(activityFormEntity.getId());
        emailNotification.setActivityForm(activityForm);

        // Save the email notification
        EmailNotification savedNotification = emailNotificationRepository.save(emailNotification);

        // Convert to resource
        return toResource(savedNotification);
    }

    /**
     * Find all email notifications
     *
     * @return List of email notification resources
     */
    public List<EmailNotificationResource> findAllEmailNotifications() {
        return emailNotificationRepository.findAll().stream()
                .map(this::toResource)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Find email notifications by activity form ID
     *
     * @param activityFormId The activity form ID
     * @return List of email notification resources
     */
    public List<EmailNotificationResource> findEmailNotificationsByActivityFormId(Long activityFormId) {
        return emailNotificationRepository.findByActivityFormId(activityFormId).stream()
                .map(this::toResource)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Find email notifications by status
     *
     * @param status The status
     * @return List of email notification resources
     */
    public List<EmailNotificationResource> findEmailNotificationsByStatus(String status) {
        return emailNotificationRepository.findByStatus(status).stream()
                .map(this::toResource)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Find email notifications by recipient
     *
     * @param recipient The recipient
     * @return List of email notification resources
     */
    public List<EmailNotificationResource> findEmailNotificationsByRecipient(String recipient) {
        return emailNotificationRepository.findByRecipient(recipient).stream()
                .map(this::toResource)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Convert an email notification to a resource
     *
     * @param notification The email notification
     * @return The email notification resource
     */
    private EmailNotificationResource toResource(EmailNotification notification) {
        EmailNotificationResource resource = new EmailNotificationResource();
        resource.setId(notification.getId());
        resource.setRecipient(notification.getRecipient());
        resource.setSubject(notification.getSubject());
        resource.setTemplateName(notification.getTemplateName());
        resource.setSentAt(notification.getSentAt());
        resource.setStatus(notification.getStatus());
        resource.setTemplateData(notification.getTemplateData());

        if (notification.getActivityForm() != null) {
            resource.setActivityFormId(notification.getActivityForm().getId());
        }

        return resource;
    }

    /**
     * Creates email content with relevant activity form data
     *
     * @param activityForm The activity form
     * @param recipient    The email recipient (student or supervisor)
     * @param isStudent    Whether the recipient is the student
     * @return Formatted email content
     */
    public String createEmailContent(ActivityForm activityForm, User recipient, boolean isStudent) {
        StringBuilder content = new StringBuilder();

        // Greeting
        content.append("Dear ").append(recipient.getFirstName()).append(" ").append(recipient.getLastName()).append(",\n\n");

        // Main message based on recipient type
        if (isStudent) {
            content.append("Your activity form has been updated to status: ").append(activityForm.getStatus()).append(".\n\n");
        } else {
            content.append("An activity form you supervise has been updated to status: ").append(activityForm.getStatus()).append(".\n\n");
        }

        // Activity details
        content.append("Activity Details:\n");
        content.append("- Activity Type: ").append(activityForm.getActivityType()).append("\n");
        content.append("- Activity Date: ").append(activityForm.getActivityDate()).append("\n");
        content.append("- Organizing Entity: ").append(activityForm.getOrganizingEntity()).append("\n");
        content.append("- Location: ").append(activityForm.getLocation()).append("\n");
        content.append("- Start Time: ").append(activityForm.getStartTime()).append("\n");
        content.append("- End Time: ").append(activityForm.getEndTime()).append("\n");
        content.append("- Description: ").append(activityForm.getDescription()).append("\n\n");

        // If rejected, include reason
        if (activityForm.getRejectionReason() != null && !activityForm.getRejectionReason().isEmpty()) {
            content.append("Rejection Reason: ").append(activityForm.getRejectionReason()).append("\n\n");
        }

        // Closing
        content.append("Please log in to the system for more details.\n\n");
        content.append("Best regards,\nGraduation Project System");

        return content.toString();
    }

    /**
     * Gets the email recipient based on the activity form status
     *
     * @param status The activity form status
     * @return The email recipient for the given status, or null if not configured
     */
    public String getStatusBasedRecipient(Status status) {
        String propertyKey = "email.recipient." + status.name();
        return environment.getProperty(propertyKey);
    }
}
