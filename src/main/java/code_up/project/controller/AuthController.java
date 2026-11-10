package code_up.project.controller;

import code_up.project.dtos.UserDto.UserLoginRequest;
import code_up.project.dtos.UserDto.UserLoginResponse;
import code_up.project.services.AuthServices.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "authentication", description = "provides login and authentication")
@RestController
@RequestMapping("/api/{version}/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @PostMapping(path = "/login", version = "1")
    public UserLoginResponse login(@RequestBody UserLoginRequest req) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.email(),
                        req.password()
                )
        );

        UserDetails user = userDetailsService.loadUserByUsername(req.email());

        return new UserLoginResponse(jwtService.generateToken(user));
    }

}
