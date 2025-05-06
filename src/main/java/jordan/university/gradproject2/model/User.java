package jordan.university.gradproject2.model;

import jordan.university.gradproject2.enums.Faculty;
import jordan.university.gradproject2.enums.Occupation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String universityId;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private Faculty faculty;
    private Occupation occupation;
}
