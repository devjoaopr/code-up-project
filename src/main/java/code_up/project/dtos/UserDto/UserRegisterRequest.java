package code_up.project.dtos.UserDto;

import code_up.project.shared.enums.Role;

public record UserRegisterRequest(
        String username,
        String email,
        String password,
        Role roles
) {
}
