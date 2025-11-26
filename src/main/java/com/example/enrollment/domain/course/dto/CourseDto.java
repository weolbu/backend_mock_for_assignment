package com.example.enrollment.domain.course.dto;

import com.example.enrollment.domain.course.entity.Course;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class CourseDto {

    @Getter
    @NoArgsConstructor
    public static class CreateRequest {
        @NotBlank(message = "강의 제목은 필수입니다")
        private String title;

        private String description;

        @NotBlank(message = "강사명은 필수입니다")
        private String instructorName;

        @NotNull(message = "최대 수강 인원은 필수입니다")
        @Min(value = 1, message = "최대 수강 인원은 1명 이상이어야 합니다")
        private Integer maxStudents;
    }

    @Getter
    public static class Response {
        private final Long id;
        private final String title;
        private final String description;
        private final String instructorName;
        private final Integer maxStudents;
        private final Integer currentStudents;
        private final Integer availableSeats;
        private final Boolean isFull;
        private final LocalDateTime createdAt;

        public Response(Course course) {
            this.id = course.getId();
            this.title = course.getTitle();
            this.description = course.getDescription();
            this.instructorName = course.getInstructorName();
            this.maxStudents = course.getMaxStudents();
            this.currentStudents = course.getCurrentStudents();
            this.availableSeats = course.getAvailableSeats();
            this.isFull = course.isFull();
            this.createdAt = course.getCreatedAt();
        }
    }

    @Getter
    public static class ListResponse {
        private final Long id;
        private final String title;
        private final String instructorName;
        private final Integer maxStudents;
        private final Integer currentStudents;
        private final Integer availableSeats;
        private final Boolean isFull;

        public ListResponse(Course course) {
            this.id = course.getId();
            this.title = course.getTitle();
            this.instructorName = course.getInstructorName();
            this.maxStudents = course.getMaxStudents();
            this.currentStudents = course.getCurrentStudents();
            this.availableSeats = course.getAvailableSeats();
            this.isFull = course.isFull();
        }
    }
}
