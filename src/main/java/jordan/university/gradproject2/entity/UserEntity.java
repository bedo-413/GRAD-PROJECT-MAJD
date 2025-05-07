package jordan.university.gradproject2.entity;

import jordan.university.gradproject2.enums.Faculty;
import jordan.university.gradproject2.enums.Occupation;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserEntity {
    private String universityId;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private Faculty faculty;
    private Occupation occupation;
}
