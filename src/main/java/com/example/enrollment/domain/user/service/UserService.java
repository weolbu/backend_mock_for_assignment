package com.example.enrollment.domain.user.service;

import com.example.enrollment.domain.user.dto.UserDto;
import com.example.enrollment.domain.user.entity.User;
import com.example.enrollment.domain.user.repository.UserRepository;
import com.example.enrollment.global.exception.BusinessException;
import com.example.enrollment.global.exception.ErrorCode;
import com.example.enrollment.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserDto.SignupResponse signup(UserDto.SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();

        User savedUser = userRepository.save(user);

        return new UserDto.SignupResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getName()
        );
    }

    public UserDto.LoginResponse login(UserDto.LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }

        String token = jwtTokenProvider.createToken(user.getId(), user.getEmail());
        UserDto.UserInfo userInfo = new UserDto.UserInfo(user.getId(), user.getEmail(), user.getName());

        return new UserDto.LoginResponse(token, userInfo);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }
}
