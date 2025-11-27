package com.example.enrollment.domain.enrollment.dto;

import com.example.enrollment.domain.enrollment.entity.Enrollment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class EnrollmentDto {

    @Schema(description = "배치 수강 신청 요청")
    @Getter
    @NoArgsConstructor
    public static class BatchRequest {
        @Schema(description = "수강 신청할 강의 ID 목록", example = "[1, 2, 3]", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotEmpty(message = "강의 ID 목록은 필수입니다")
        private List<Long> courseIds;
    }

    @Schema(description = "배치 수강 신청 응답 (부분 성공 지원)")
    @Getter
    public static class BatchResponse {
        @Schema(description = "성공한 수강 신청 목록")
        private final List<SuccessItem> success;

        @Schema(description = "실패한 수강 신청 목록")
        private final List<FailedItem> failed;

        public BatchResponse(List<SuccessItem> success, List<FailedItem> failed) {
            this.success = success;
            this.failed = failed;
        }
    }

    @Schema(description = "배치 수강 신청 성공 항목")
    @Getter
    public static class SuccessItem {
        @Schema(description = "수강 신청 ID", example = "1")
        private final Long enrollmentId;

        @Schema(description = "강의 ID", example = "1")
        private final Long courseId;

        @Schema(description = "강의 제목", example = "부동산 투자 기초")
        private final String courseTitle;

        public SuccessItem(Enrollment enrollment) {
            this.enrollmentId = enrollment.getId();
            this.courseId = enrollment.getCourse().getId();
            this.courseTitle = enrollment.getCourse().getTitle();
        }
    }

    @Schema(description = "배치 수강 신청 실패 항목")
    @Getter
    public static class FailedItem {
        @Schema(description = "강의 ID", example = "3")
        private final Long courseId;

        @Schema(description = "실패 사유", example = "이미 수강 신청한 강의입니다")
        private final String reason;

        public FailedItem(Long courseId, String reason) {
            this.courseId = courseId;
            this.reason = reason;
        }
    }

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
