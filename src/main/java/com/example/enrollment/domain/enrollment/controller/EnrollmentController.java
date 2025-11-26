package com.example.enrollment.domain.enrollment.controller;

import com.example.enrollment.domain.enrollment.dto.EnrollmentDto;
import com.example.enrollment.domain.enrollment.service.EnrollmentService;
import com.example.enrollment.global.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping("/{courseId}/enroll")
    public ResponseEntity<EnrollmentDto.Response> enroll(
            @PathVariable Long courseId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        EnrollmentDto.Response response = enrollmentService.enroll(userPrincipal.getUserId(), courseId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
