package com.example.enrollment.domain.course.controller;

import com.example.enrollment.domain.course.dto.CourseDto;
import com.example.enrollment.domain.course.service.CourseService;
import com.example.enrollment.global.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "2. Course", description = "강의 API - 강의 조회 및 등록")
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @Operation(
            summary = "강의 등록 🔒",
            description = "새로운 강의를 등록합니다. **인증이 필요합니다.** (Authorization 헤더에 Bearer 토큰 필요)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "강의 등록 성공",
                    content = @Content(schema = @Schema(implementation = CourseDto.Response.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (유효성 검증 실패)",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"code\":\"G001\",\"message\":\"강의 제목은 필수입니다\",\"timestamp\":\"2024-01-15T14:30:00\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 필요",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"code\":\"A003\",\"message\":\"인증이 필요합니다\",\"timestamp\":\"2024-01-15T14:30:00\"}")
                    )
            )
    })
    @PostMapping
    public ResponseEntity<CourseDto.Response> createCourse(@Valid @RequestBody CourseDto.CreateRequest request) {
        CourseDto.Response response = courseService.createCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "강의 목록 조회",
            description = "등록된 모든 강의 목록을 조회합니다. 인증 없이 호출할 수 있습니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CourseDto.ListResponse.class)))
            )
    })
    @GetMapping
    public ResponseEntity<List<CourseDto.ListResponse>> getAllCourses() {
        List<CourseDto.ListResponse> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @Operation(
            summary = "강의 상세 조회",
            description = "특정 강의의 상세 정보를 조회합니다. 인증 없이 호출할 수 있습니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = CourseDto.Response.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "강의 없음",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"code\":\"C001\",\"message\":\"강의를 찾을 수 없습니다\",\"timestamp\":\"2024-01-15T14:30:00\"}")
                    )
            )
    })
    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDto.Response> getCourse(
            @Parameter(description = "강의 ID", example = "1")
            @PathVariable Long courseId) {
        CourseDto.Response response = courseService.getCourse(courseId);
        return ResponseEntity.ok(response);
    }
}
