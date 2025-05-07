package jordan.university.gradproject2.repository.user;

import jordan.university.gradproject2.mapper.UserMapper;
import jordan.university.gradproject2.model.User;

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
}
