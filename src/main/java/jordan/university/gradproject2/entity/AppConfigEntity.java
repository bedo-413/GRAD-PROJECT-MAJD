package jordan.university.gradproject2.entity;

import jakarta.persistence.*;
import jordan.university.gradproject2.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "app_configs")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class AppConfigEntity extends BaseEntity {

    @Column(name = "config_key", nullable = false, unique = true)
    private String key;

    @Column(name = "config_value", nullable = false, columnDefinition = "TEXT")
    private String value;
}