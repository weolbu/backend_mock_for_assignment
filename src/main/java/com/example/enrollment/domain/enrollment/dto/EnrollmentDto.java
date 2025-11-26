package com.example.enrollment.domain.enrollment.dto;

import com.example.enrollment.domain.enrollment.entity.Enrollment;
import lombok.Getter;

import java.time.LocalDateTime;

public class EnrollmentDto {

    @Getter
    public static class Response {
        private final Long enrollmentId;
        private final Long courseId;
        private final String courseTitle;
        private final String instructorName;
        private final Long userId;
        private final String userName;
        private final LocalDateTime enrolledAt;
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
