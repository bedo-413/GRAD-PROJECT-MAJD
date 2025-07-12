package jordan.university.gradproject2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppConfig {
    private Long id;
    private String uuid;
    private String key;
    private String value;
}