package jordan.university.gradproject2.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailResource {
    private String to;
    private String subject;
    private String name;
}
