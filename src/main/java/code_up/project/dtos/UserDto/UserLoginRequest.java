package code_up.project.dtos.UserDto;

public record UserLoginRequest(
        String email,
        String password
) {
}
