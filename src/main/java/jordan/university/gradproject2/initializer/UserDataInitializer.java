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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

        List<UserEntity> inputUsers = mapper.readValue(inputStream, new TypeReference<>() {});
        Map<String, UserEntity> existingUsers = userJpaRepository
                .findAll()
                .stream()
                .collect(Collectors.toMap(UserEntity::getUniversityId, u -> u));

        List<UserEntity> toSave = new ArrayList<>();

        for (UserEntity inputUser : inputUsers) {
            UserEntity existing = existingUsers.get(inputUser.getUniversityId());

            inputUser.setPassword(passwordEncoder.encode(inputUser.getPassword())); // Always re-hash

            if (existing == null) {
                toSave.add(inputUser); // New user
            } else if (hasChanged(existing, inputUser)) {
                inputUser.setId(existing.getId()); // Ensure update, not insert
                toSave.add(inputUser); // Update if changed
            }
        }

        if (!toSave.isEmpty()) {
            userJpaRepository.saveAll(toSave);
            System.out.println("✅ Synced " + toSave.size() + " users.");
        } else {
            System.out.println("ℹ️ No user updates needed.");
        }
    }

    private boolean hasChanged(UserEntity existing, UserEntity incoming) {
        return !Objects.equals(existing.getEmail(), incoming.getEmail())
                || !Objects.equals(existing.getPhoneNumber(), incoming.getPhoneNumber())
                || !Objects.equals(existing.getFirstName(), incoming.getFirstName())
                || !Objects.equals(existing.getMiddleName(), incoming.getMiddleName())
                || !Objects.equals(existing.getLastName(), incoming.getLastName())
                || !Objects.equals(existing.getFaculty(), incoming.getFaculty())
                || !Objects.equals(existing.getOccupation(), incoming.getOccupation())
                || !passwordEncoder.matches(incoming.getPassword(), existing.getPassword());
    }
}
