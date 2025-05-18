package jordan.university.gradproject2.request;

import jordan.university.gradproject2.enums.Faculty;
import jordan.university.gradproject2.enums.Occupation;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequest {
    private String universityId;
    private String email;
    private String password;
    private String phoneNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private Faculty faculty;
    private Occupation occupation;
}