package com.example.enrollment.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {

    @Getter
    @NoArgsConstructor
    public static class SignupRequest {
        @NotBlank(message = "이메일은 필수입니다")
        @Email(message = "올바른 이메일 형식이 아닙니다")
        private String email;

        @NotBlank(message = "비밀번호는 필수입니다")
        @Size(min = 4, message = "비밀번호는 4자 이상이어야 합니다")
        private String password;

        @NotBlank(message = "이름은 필수입니다")
        private String name;
    }

    @Getter
    @NoArgsConstructor
    public static class LoginRequest {
        @NotBlank(message = "이메일은 필수입니다")
        @Email(message = "올바른 이메일 형식이 아닙니다")
        private String email;

        @NotBlank(message = "비밀번호는 필수입니다")
        private String password;
    }

    @Getter
    public static class LoginResponse {
        private final String accessToken;
        private final String tokenType = "Bearer";
        private final UserInfo user;

        public LoginResponse(String accessToken, UserInfo user) {
            this.accessToken = accessToken;
            this.user = user;
        }
    }

    @Getter
    public static class UserInfo {
        private final Long id;
        private final String email;
        private final String name;

        public UserInfo(Long id, String email, String name) {
            this.id = id;
            this.email = email;
            this.name = name;
        }
    }

    @Getter
    public static class SignupResponse {
        private final Long id;
        private final String email;
        private final String name;
        private final String message = "회원가입이 완료되었습니다";

        public SignupResponse(Long id, String email, String name) {
            this.id = id;
            this.email = email;
            this.name = name;
        }
    }
}
