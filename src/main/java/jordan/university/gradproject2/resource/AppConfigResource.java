package jordan.university.gradproject2.resource;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AppConfigResource {
    private Long id;
    private String uuid;
    private String key;
    private String value;
    private LocalDate createdAt;
    private LocalDateTime lastUpdatedAt;
}