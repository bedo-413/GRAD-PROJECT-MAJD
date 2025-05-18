package jordan.university.gradproject2.security;

import jordan.university.gradproject2.model.User;
import jordan.university.gradproject2.repository.user.UserRepository;
import jordan.university.gradproject2.request.LoginRequest;
import jordan.university.gradproject2.security.jwt.JwtAuthResponse;
import jordan.university.gradproject2.security.jwt.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider,
                          UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Fetch user by email
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Compare passwords directly (PLAIN TEXT) — not recommended for production
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // Generate fake Spring Authentication (if you still want to keep security context)
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(), null, List.of()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        String token = jwtTokenProvider.generateToken(authentication);

        // Return token + user data
        return ResponseEntity.ok(new JwtAuthResponse(token, user));
    }


//    @PostMapping("/login")
//    public ResponseEntity<JwtAuthResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
//        // Authenticate user using Spring Security
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginRequest.getEmail(),
//                        loginRequest.getPassword()
//                )
//        );
//
//        // Set authentication in context
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // Generate JWT token
//        String token = jwtTokenProvider.generateToken(authentication);
//
//        // Fetch full User entity
//        User user = userRepository.findByEmail(loginRequest.getEmail())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // Return JWT + full user info
//        return ResponseEntity.ok(new JwtAuthResponse(token, user));
//    }
}
