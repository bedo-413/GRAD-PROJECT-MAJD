package jordan.university.gradproject2.security.jwt;

import jordan.university.gradproject2.model.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private User user;

    public JwtAuthResponse(String accessToken, User user) {
        this.accessToken = accessToken;
        this.user = user;
    }
}