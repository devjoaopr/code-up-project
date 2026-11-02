package code_up.project.mappers;

import code_up.project.dtos.UserDto.UserRegisterRequest;
import code_up.project.dtos.UserDto.UserRegisterResponse;
import code_up.project.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserRegisterRequest toDto(User user);

    User toEntity(UserRegisterRequest dto);

    UserRegisterResponse toResponse(User dto);

    List<UserRegisterResponse> toResponse(List<User> dtos);
}
