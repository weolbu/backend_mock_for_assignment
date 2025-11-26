package com.example.enrollment.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // User
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "U001", "이미 사용 중인 이메일입니다"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U002", "사용자를 찾을 수 없습니다"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "U003", "비밀번호가 일치하지 않습니다"),

    // Course
    COURSE_NOT_FOUND(HttpStatus.NOT_FOUND, "C001", "강의를 찾을 수 없습니다"),
    COURSE_FULL(HttpStatus.BAD_REQUEST, "C002", "수강 정원이 초과되었습니다"),

    // Enrollment
    ALREADY_ENROLLED(HttpStatus.CONFLICT, "E001", "이미 수강 신청한 강의입니다"),

    // Auth
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A001", "유효하지 않은 토큰입니다"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "A002", "만료된 토큰입니다"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "A003", "인증이 필요합니다"),

    // Common
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "G001", "잘못된 입력입니다"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G002", "서버 오류가 발생했습니다");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
