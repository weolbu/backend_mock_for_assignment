package com.example.enrollment.domain.course.dto;

import com.example.enrollment.domain.course.entity.Course;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class CourseDto {

    @Schema(description = "강의 등록 요청")
    @Getter
    @NoArgsConstructor
    public static class CreateRequest {
        @Schema(description = "강의 제목", example = "React 기초 마스터", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "강의 제목은 필수입니다")
        private String title;

        @Schema(description = "강의 설명", example = "React의 기본 개념부터 Hooks까지 배웁니다.")
        private String description;

        @Schema(description = "강사 이름", example = "김강사", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "강사명은 필수입니다")
        private String instructorName;

        @Schema(description = "최대 수강 인원", example = "30", minimum = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "최대 수강 인원은 필수입니다")
        @Min(value = 1, message = "최대 수강 인원은 1명 이상이어야 합니다")
        private Integer maxStudents;

        @Schema(description = "수강료 (원)", example = "100000", minimum = "0", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "수강료는 필수입니다")
        @Min(value = 0, message = "수강료는 0원 이상이어야 합니다")
        private Integer price;
    }

    @Schema(description = "강의 상세 응답")
    @Getter
    public static class Response {
        @Schema(description = "강의 ID", example = "1")
        private final Long id;

        @Schema(description = "강의 제목", example = "부동산 투자 기초")
        private final String title;

        @Schema(description = "강의 설명", example = "부동산 투자의 기본 개념과 전략을 배웁니다.")
        private final String description;

        @Schema(description = "강사 이름", example = "김투자")
        private final String instructorName;

        @Schema(description = "최대 수강 인원", example = "30")
        private final Integer maxStudents;

        @Schema(description = "현재 수강 인원", example = "5")
        private final Integer currentStudents;

        @Schema(description = "남은 자리", example = "25")
        private final Integer availableSeats;

        @Schema(description = "정원 마감 여부", example = "false")
        private final Boolean isFull;

        @Schema(description = "수강료 (원)", example = "100000")
        private final Integer price;

        @Schema(description = "강의 등록일시", example = "2024-01-15T09:00:00")
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
            this.price = course.getPrice();
            this.createdAt = course.getCreatedAt();
        }
    }

    @Schema(description = "강의 목록 응답")
    @Getter
    public static class ListResponse {
        @Schema(description = "강의 ID", example = "1")
        private final Long id;

        @Schema(description = "강의 제목", example = "부동산 투자 기초")
        private final String title;

        @Schema(description = "강사 이름", example = "김투자")
        private final String instructorName;

        @Schema(description = "최대 수강 인원", example = "30")
        private final Integer maxStudents;

        @Schema(description = "현재 수강 인원", example = "5")
        private final Integer currentStudents;

        @Schema(description = "남은 자리", example = "25")
        private final Integer availableSeats;

        @Schema(description = "정원 마감 여부", example = "false")
        private final Boolean isFull;

        @Schema(description = "수강료 (원)", example = "100000")
        private final Integer price;

        @Schema(description = "강의 등록일시", example = "2024-01-15T09:00:00")
        private final LocalDateTime createdAt;

        public ListResponse(Course course) {
            this.id = course.getId();
            this.title = course.getTitle();
            this.instructorName = course.getInstructorName();
            this.maxStudents = course.getMaxStudents();
            this.currentStudents = course.getCurrentStudents();
            this.availableSeats = course.getAvailableSeats();
            this.isFull = course.isFull();
            this.price = course.getPrice();
            this.createdAt = course.getCreatedAt();
        }
    }
}
