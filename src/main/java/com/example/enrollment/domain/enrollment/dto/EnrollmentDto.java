package com.example.enrollment.domain.enrollment.dto;

import com.example.enrollment.domain.enrollment.entity.Enrollment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

public class EnrollmentDto {

    @Schema(description = "수강 신청 응답")
    @Getter
    public static class Response {
        @Schema(description = "수강 신청 ID", example = "1")
        private final Long enrollmentId;

        @Schema(description = "강의 ID", example = "1")
        private final Long courseId;

        @Schema(description = "강의 제목", example = "부동산 투자 기초")
        private final String courseTitle;

        @Schema(description = "강사 이름", example = "김투자")
        private final String instructorName;

        @Schema(description = "수강생 ID", example = "1")
        private final Long userId;

        @Schema(description = "수강생 이름", example = "홍길동")
        private final String userName;

        @Schema(description = "수강 신청 일시", example = "2024-01-15T14:30:00")
        private final LocalDateTime enrolledAt;

        @Schema(description = "결과 메시지", example = "수강 신청이 완료되었습니다")
        private final String message = "수강 신청이 완료되었습니다";

        public Response(Enrollment enrollment) {
            this.enrollmentId = enrollment.getId();
            this.courseId = enrollment.getCourse().getId();
            this.courseTitle = enrollment.getCourse().getTitle();
            this.instructorName = enrollment.getCourse().getInstructorName();
            this.userId = enrollment.getUser().getId();
            this.userName = enrollment.getUser().getName();
            this.enrolledAt = enrollment.getEnrolledAt();
        }
    }
}
