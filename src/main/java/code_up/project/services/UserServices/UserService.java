package code_up.project.services.UserServices;

import code_up.project.dtos.UserDto.UserRegisterRequest;
import code_up.project.dtos.UserDto.UserRegisterResponse;
import code_up.project.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    
}
