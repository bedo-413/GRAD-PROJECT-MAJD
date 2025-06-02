package jordan.university.gradproject2.repository.email;

import jordan.university.gradproject2.model.EmailNotification;

import java.util.List;
import java.util.Optional;

public interface EmailNotificationRepository {
    
    EmailNotification save(EmailNotification emailNotification);
    
    Optional<EmailNotification> findById(Long id);
    
    List<EmailNotification> findAll();
    
    List<EmailNotification> findByActivityFormId(Long activityFormId);
    
    List<EmailNotification> findByStatus(String status);
    
    List<EmailNotification> findByRecipient(String recipient);
    
    void deleteById(Long id);
}