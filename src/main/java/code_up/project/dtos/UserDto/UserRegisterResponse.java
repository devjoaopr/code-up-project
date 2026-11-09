package code_up.project.dtos.UserDto;

import code_up.project.shared.enums.Role;

import java.util.UUID;

public record UserRegisterResponse(
        UUID id,
        String username,
        String email,
        String password,
        Role roles
) {
}
