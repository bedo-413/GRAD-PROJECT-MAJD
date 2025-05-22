package jordan.university.gradproject2.service;

import jakarta.transaction.Transactional;
import jordan.university.gradproject2.enums.Occupation;
import jordan.university.gradproject2.mapper.UserMapper;
import jordan.university.gradproject2.repository.user.UserRepository;
import jordan.university.gradproject2.resource.UserResource;

import java.util.List;

public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional()
    public List<UserResource> getUsersByOccupation(Occupation occupation, String keyword) {
        return userMapper.toResources(userRepository.searchByOccupationWithKeyword(occupation, keyword));
    }

    @Transactional()
    public List<UserResource> getUsersByUniversityId(String keyword) {
        return userMapper.toResources(userRepository.searchByUniversityId(keyword));
    }
}
