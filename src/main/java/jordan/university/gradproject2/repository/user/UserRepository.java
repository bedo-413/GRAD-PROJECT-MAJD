package jordan.university.gradproject2.repository.user;

import jordan.university.gradproject2.enums.Occupation;
import jordan.university.gradproject2.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    Optional<User> findByUniversityId(String universityId);

    List<User> searchByOccupationWithKeyword(Occupation occupation, String keyword);

    List<User> searchByUniversityId(String universityId);
}
