package jordan.university.gradproject2.repository.user;

import jordan.university.gradproject2.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);
}
