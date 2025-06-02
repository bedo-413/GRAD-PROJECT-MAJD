package jordan.university.gradproject2.repository.email;

import jordan.university.gradproject2.entity.EmailNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailNotificationJpaRepository extends JpaRepository<EmailNotificationEntity, Long>, JpaSpecificationExecutor<EmailNotificationEntity> {
    
    List<EmailNotificationEntity> findByActivityFormId(Long activityFormId);
    
    List<EmailNotificationEntity> findByStatus(String status);
    
    List<EmailNotificationEntity> findByRecipient(String recipient);
}