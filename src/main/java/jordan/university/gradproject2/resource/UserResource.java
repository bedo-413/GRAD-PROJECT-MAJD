package jordan.university.gradproject2.resource;

import jordan.university.gradproject2.enums.Faculty;
import jordan.university.gradproject2.enums.Occupation;
import lombok.Data;

@Data
public class UserResource {
    private Long id;
    private String universityId;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private Faculty faculty;
    private Occupation occupation;
}
