package jordan.university.gradproject2.repository.user;

import jordan.university.gradproject2.entity.UserEntity;
import jordan.university.gradproject2.enums.Occupation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    UserEntity findByEmail(String email);

    UserEntity findByUniversityId(String universityId);

    @Query("SELECT u FROM UserEntity u WHERE u.occupation = :occupation AND " +
            "LOWER(CONCAT(u.firstName, ' ', u.middleName, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<UserEntity> searchByOccupationWithKeyword(@Param("occupation") Occupation occupation,
                                                   @Param("keyword") String keyword);

    @Query("SELECT u FROM UserEntity u WHERE LOWER(u.universityId) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<UserEntity> searchByUniversityIdContaining(@Param("keyword") String keyword);

}
