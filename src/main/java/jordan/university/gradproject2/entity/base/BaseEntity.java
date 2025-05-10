package jordan.university.gradproject2.entity.base;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Id
    @GeneratedValue
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
        if (nonNull(uuid)) {
            uuid = UUID.randomUUID().toString();
        }
    }
}
