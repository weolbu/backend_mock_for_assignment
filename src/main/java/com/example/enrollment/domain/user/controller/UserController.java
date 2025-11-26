package com.example.enrollment.domain.user.controller;

import com.example.enrollment.domain.user.dto.UserDto;
import com.example.enrollment.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto.SignupResponse> signup(@Valid @RequestBody UserDto.SignupRequest request) {
        UserDto.SignupResponse response = userService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto.LoginResponse> login(@Valid @RequestBody UserDto.LoginRequest request) {
        UserDto.LoginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}
