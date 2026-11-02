package code_up.project.controller;

import code_up.project.dtos.UserDto.UserRegisterRequest;
import code_up.project.dtos.UserDto.UserRegisterResponse;
import code_up.project.services.UserServices.UserService;
import code_up.project.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/{version}/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping(path = "", version = "1")
    public ResponseEntity<ApiResponse<UserRegisterResponse>> register(@RequestBody UserRegisterRequest request) {
        return ResponseEntity.ok(ApiResponse.success(service.register(request), "user created successfully"));
    }
}
