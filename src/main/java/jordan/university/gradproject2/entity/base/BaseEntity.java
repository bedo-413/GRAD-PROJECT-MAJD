package jordan.university.gradproject2.entity.base;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdAt;

    @LastModifiedDate
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime lastUpdatedAt;

    @Column(name = "SYS_UUID")
    private String uuid;

    @PrePersist
    public void prePersistUUID() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
    }

    @PreUpdate
    public void preUpdateTimestamps() {
        lastUpdatedAt = LocalDateTime.now();
    }
}
