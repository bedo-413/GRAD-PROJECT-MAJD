package jordan.university.gradproject2.initializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jordan.university.gradproject2.entity.UserEntity;
import jordan.university.gradproject2.repository.user.UserJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
@Profile("init-configs")
public class UserDataInitializer implements CommandLineRunner {

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDataInitializer(UserJpaRepository userJpaRepository, PasswordEncoder passwordEncoder) {
        this.userJpaRepository = userJpaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = getClass().getResourceAsStream("/init-data/init-users.json");

        if (inputStream == null) {
            System.out.println("❌ init-users.json not found.");
            return;
        }

        List<UserEntity> users = mapper.readValue(inputStream, new TypeReference<List<UserEntity>>() {
        });

        for (UserEntity user : users) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userJpaRepository.saveAll(users);
        System.out.println("✅ Initial users loaded and passwords hashed.");
    }
}
