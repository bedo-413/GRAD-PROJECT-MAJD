package jordan.university.gradproject2.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.internet.MimeMessage;
import jordan.university.gradproject2.entity.ActivityFormEntity;
import jordan.university.gradproject2.enums.Status;
import jordan.university.gradproject2.model.ActivityForm;
import jordan.university.gradproject2.model.EmailNotification;
import jordan.university.gradproject2.repository.activity.ActivityFormJpaRepository;
import jordan.university.gradproject2.repository.email.EmailNotificationRepository;
import jordan.university.gradproject2.request.EmailNotificationRequest;
import jordan.university.gradproject2.resource.EmailNotificationResource;
import jordan.university.gradproject2.taskcatalog.StatusTransitionManagerV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.nonNull;
import static jordan.university.gradproject2.enums.WorkflowAction.APPROVE;

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

    @Value("${application.base-url:http://localhost:8081}")
    private String baseUrl;

    @Override
    public void sendNotification(String recipient, String subject, ActivityForm activityForm) {
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setRecipient(recipient);
        emailNotification.setSubject(subject);
        emailNotification.setTemplateName("simple");
        emailNotification.setStatus("PENDING");
        emailNotification.setActivityForm(activityForm);
        EmailNotification savedNotification = emailNotificationRepository.save(emailNotification);
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(fromEmail, applicationName);
            helper.setTo(recipient);
            helper.setSubject(subject);

            Context context = new Context();
            context.setVariable("studentName", activityForm.getStudent().getFirstName() + " " + activityForm.getStudent().getLastName());
            context.setVariable("formUuid", activityForm.getUuid());
            context.setVariable("formTitle", activityForm.getActivityType());
            context.setVariable("previousStatus", activityForm.getStatus().name());
            context.setVariable("currentStatus", StatusTransitionManagerV2.getNextStatus(activityForm, activityForm.getStatus(), nonNull(activityForm.getWorkflowAction()) ? activityForm.getWorkflowAction() : APPROVE).get().name());
            context.setVariable("baseUrl", baseUrl);

            String content = templateEngine.process("activity-status-update", context);
            helper.setText(content, true);

            mailSender.send(mimeMessage);

            savedNotification.setStatus("SENT");
            savedNotification.setSentAt(LocalDateTime.now());
            savedNotification = emailNotificationRepository.save(savedNotification);
            log.info("Email notification sent to {} with subject: {}", recipient, subject);
            toResource(savedNotification);
        } catch (Exception e) {
            emailNotification.setStatus("FAILED");
            savedNotification = emailNotificationRepository.save(emailNotification);
            log.error("Failed to send email notification to {}", recipient, e);
            toResource(savedNotification);
        }
    }

    private void updateEmailStatus(String recipient, String subject, String status) {
        List<EmailNotification> notifications = emailNotificationRepository.findByRecipient(recipient);
        for (EmailNotification notification : notifications) {
            if (notification.getSubject().equals(subject) &&
                    ("PENDING".equals(notification.getStatus()) || "FAILED".equals(notification.getStatus()))) {
                notification.setStatus(status);
                if ("SENT".equals(status)) {
                    notification.setSentAt(LocalDateTime.now());
                }
                emailNotificationRepository.save(notification);
                break;
            }
        }
    }

    public EmailNotificationResource createEmailNotification(EmailNotificationRequest request) {
        ActivityFormEntity activityFormEntity = activityFormJpaRepository.findById(request.getActivityFormId())
                .orElseThrow(() -> new IllegalArgumentException("Activity form not found with ID: " + request.getActivityFormId()));

        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setRecipient(request.getRecipient());
        emailNotification.setSubject(request.getSubject());
        emailNotification.setTemplateName(request.getTemplateName());
        emailNotification.setTemplateData(request.getTemplateData());
        emailNotification.setStatus("PENDING");

        ActivityForm activityForm = new ActivityForm();
        activityForm.setId(activityFormEntity.getId());
        emailNotification.setActivityForm(activityForm);
        EmailNotification savedNotification = emailNotificationRepository.save(emailNotification);
        return toResource(savedNotification);
    }

    public List<EmailNotificationResource> findAllEmailNotifications() {
        return emailNotificationRepository.findAll().stream()
                .map(this::toResource)
                .collect(java.util.stream.Collectors.toList());
    }

    public List<EmailNotificationResource> findEmailNotificationsByActivityFormId(Long activityFormId) {
        return emailNotificationRepository.findByActivityFormId(activityFormId).stream()
                .map(this::toResource)
                .collect(java.util.stream.Collectors.toList());
    }

    public List<EmailNotificationResource> findEmailNotificationsByStatus(String status) {
        return emailNotificationRepository.findByStatus(status).stream()
                .map(this::toResource)
                .collect(java.util.stream.Collectors.toList());
    }

    public List<EmailNotificationResource> findEmailNotificationsByRecipient(String recipient) {
        return emailNotificationRepository.findByRecipient(recipient).stream()
                .map(this::toResource)
                .collect(java.util.stream.Collectors.toList());
    }

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

    public String getStatusBasedRecipient(Status status) {
        String propertyKey = "email.recipient." + status.name();
        return environment.getProperty(propertyKey);
    }
}
