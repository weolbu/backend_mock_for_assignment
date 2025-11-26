package com.example.enrollment.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {

    @Schema(description = "회원가입 요청")
    @Getter
    @NoArgsConstructor
    public static class SignupRequest {
        @Schema(description = "이메일 주소", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "이메일은 필수입니다")
        @Email(message = "올바른 이메일 형식이 아닙니다")
        private String email;

        @Schema(description = "비밀번호 (4자 이상)", example = "password123", minLength = 4, requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "비밀번호는 필수입니다")
        @Size(min = 4, message = "비밀번호는 4자 이상이어야 합니다")
        private String password;

        @Schema(description = "사용자 이름", example = "홍길동", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "이름은 필수입니다")
        private String name;
    }

    @Schema(description = "로그인 요청")
    @Getter
    @NoArgsConstructor
    public static class LoginRequest {
        @Schema(description = "이메일 주소", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "이메일은 필수입니다")
        @Email(message = "올바른 이메일 형식이 아닙니다")
        private String email;

        @Schema(description = "비밀번호", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "비밀번호는 필수입니다")
        private String password;
    }

    @Schema(description = "로그인 응답")
    @Getter
    public static class LoginResponse {
        @Schema(description = "JWT 액세스 토큰", example = "eyJhbGciOiJIUzUxMiJ9...")
        private final String accessToken;

        @Schema(description = "토큰 타입", example = "Bearer")
        private final String tokenType = "Bearer";

        @Schema(description = "사용자 정보")
        private final UserInfo user;

        public LoginResponse(String accessToken, UserInfo user) {
            this.accessToken = accessToken;
            this.user = user;
        }
    }

    @Schema(description = "사용자 정보")
    @Getter
    public static class UserInfo {
        @Schema(description = "사용자 ID", example = "1")
        private final Long id;

        @Schema(description = "이메일", example = "user@example.com")
        private final String email;

        @Schema(description = "이름", example = "홍길동")
        private final String name;

        public UserInfo(Long id, String email, String name) {
            this.id = id;
            this.email = email;
            this.name = name;
        }
    }

    @Schema(description = "회원가입 응답")
    @Getter
    public static class SignupResponse {
        @Schema(description = "생성된 사용자 ID", example = "1")
        private final Long id;

        @Schema(description = "이메일", example = "user@example.com")
        private final String email;

        @Schema(description = "이름", example = "홍길동")
        private final String name;

        @Schema(description = "결과 메시지", example = "회원가입이 완료되었습니다")
        private final String message = "회원가입이 완료되었습니다";

        public SignupResponse(Long id, String email, String name) {
            this.id = id;
            this.email = email;
            this.name = name;
        }
    }
}
