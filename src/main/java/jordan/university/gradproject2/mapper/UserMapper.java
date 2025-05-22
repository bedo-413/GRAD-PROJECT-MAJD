package jordan.university.gradproject2.mapper;

import jordan.university.gradproject2.entity.UserEntity;
import jordan.university.gradproject2.model.User;
import jordan.university.gradproject2.resource.UserResource;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toModel(UserEntity entity);

    List<User> toModels(List<UserEntity> entities);

    UserEntity toEntity(User user);

    UserResource toResource(User model);

    List<UserResource> toResources(List<User> models);
}
