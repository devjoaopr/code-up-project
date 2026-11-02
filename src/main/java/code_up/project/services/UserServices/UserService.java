package code_up.project.services.UserServices;

import code_up.project.dtos.UserDto.UserRegisterRequest;
import code_up.project.dtos.UserDto.UserRegisterResponse;
import code_up.project.entity.User;
import code_up.project.mappers.UserMapper;
import code_up.project.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserRegisterResponse register(UserRegisterRequest dto) {
        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toResponse(userRepository.save(user));
    }
}
