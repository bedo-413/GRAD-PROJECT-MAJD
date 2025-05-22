package jordan.university.gradproject2.repository.user;

import jordan.university.gradproject2.enums.Occupation;
import jordan.university.gradproject2.mapper.UserMapper;
import jordan.university.gradproject2.model.User;

import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository, UserMapper userMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userMapper.toModel(userJpaRepository.findByEmail(email)));
    }

    @Override
    public Optional<User> findByUniversityId(String universityId) {
        return Optional.ofNullable(userMapper.toModel(userJpaRepository.findByUniversityId(universityId)));
    }

    @Override
    public List<User> searchByOccupationWithKeyword(Occupation occupation, String keyword) {
        return userMapper.toModels(userJpaRepository.searchByOccupationWithKeyword(occupation, keyword));
    }

    @Override
    public List<User> searchByUniversityId(String universityId) {
        return userMapper.toModels(userJpaRepository.searchByUniversityIdContaining(universityId));
    }
}
