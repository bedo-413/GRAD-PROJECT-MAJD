package jordan.university.gradproject2.repository.email;

import jordan.university.gradproject2.entity.EmailNotificationEntity;
import jordan.university.gradproject2.mapper.EmailNotificationMapper;
import jordan.university.gradproject2.model.EmailNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class EmailNotificationRepositoryImpl implements EmailNotificationRepository {

    private final EmailNotificationJpaRepository jpaRepository;
    private final EmailNotificationMapper mapper;

    @Override
    public EmailNotification save(EmailNotification emailNotification) {
        EmailNotificationEntity entity = mapper.toEntity(emailNotification);
        return mapper.toModel(jpaRepository.save(entity));
    }

    @Override
    public Optional<EmailNotification> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toModel);
    }

    @Override
    public List<EmailNotification> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmailNotification> findByActivityFormId(Long activityFormId) {
        return jpaRepository.findByActivityFormId(activityFormId).stream()
                .map(mapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmailNotification> findByStatus(String status) {
        return jpaRepository.findByStatus(status).stream()
                .map(mapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmailNotification> findByRecipient(String recipient) {
        return jpaRepository.findByRecipient(recipient).stream()
                .map(mapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}