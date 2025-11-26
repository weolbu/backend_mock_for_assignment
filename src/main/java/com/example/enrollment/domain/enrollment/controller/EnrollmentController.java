package com.example.enrollment.domain.enrollment.controller;

import com.example.enrollment.domain.enrollment.dto.EnrollmentDto;
import com.example.enrollment.domain.enrollment.service.EnrollmentService;
import com.example.enrollment.global.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Enrollment", description = "수강 신청 API")
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Operation(summary = "수강 신청", description = "강의에 수강 신청합니다. (인증 필요)")
    @PostMapping("/{courseId}/enroll")
    public ResponseEntity<EnrollmentDto.Response> enroll(
            @PathVariable Long courseId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        EnrollmentDto.Response response = enrollmentService.enroll(userPrincipal.getUserId(), courseId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
