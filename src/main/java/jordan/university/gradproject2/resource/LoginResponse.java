package jordan.university.gradproject2.resource;

import jordan.university.gradproject2.model.User;
import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private User user;
}
