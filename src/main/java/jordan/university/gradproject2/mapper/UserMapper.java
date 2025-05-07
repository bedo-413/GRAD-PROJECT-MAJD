package jordan.university.gradproject2.mapper;

import jordan.university.gradproject2.entity.UserEntity;
import jordan.university.gradproject2.model.User;
import jordan.university.gradproject2.resource.UserResource;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toModel(UserEntity entity);

    UserEntity toEntity(User user);

    UserResource toResource(User model);
}
