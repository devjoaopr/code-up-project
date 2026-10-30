package code_up.project.dtos.UserDto;

public record UserRegisterRequest(
        String username,
        String email,
        String password
) {
}
