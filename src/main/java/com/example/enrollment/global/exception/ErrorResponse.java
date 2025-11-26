package com.example.enrollment.global.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "에러 응답")
@Getter
public class ErrorResponse {

    @Schema(description = "에러 코드", example = "U001")
    private final String code;

    @Schema(description = "에러 메시지", example = "이미 사용 중인 이메일입니다")
    private final String message;

    @Schema(description = "발생 시각", example = "2024-01-15T14:30:00")
    private final LocalDateTime timestamp;

    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
