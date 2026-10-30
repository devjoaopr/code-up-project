package code_up.project.dtos.UserDto;

import java.util.UUID;

public record UserRegisterResponse(
        UUID id,
        String username,
        String email,
        String password,
        String role
) {
}
