package jordan.university.gradproject2.repository.user;

import jordan.university.gradproject2.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    UserEntity findByEmail(String email);

    UserEntity findByUniversityId(String universityId);
}
