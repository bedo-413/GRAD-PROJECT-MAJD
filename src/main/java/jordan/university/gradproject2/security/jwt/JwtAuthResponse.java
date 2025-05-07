package jordan.university.gradproject2.security.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private String role;

    public JwtAuthResponse(String accessToken, String userId, String email, String firstName, String lastName, String role) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
}