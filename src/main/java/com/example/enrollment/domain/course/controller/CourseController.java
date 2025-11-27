package com.example.enrollment.domain.course.controller;

import com.example.enrollment.domain.course.dto.CourseDto;
import com.example.enrollment.domain.course.service.CourseService;
import com.example.enrollment.domain.user.entity.Role;
import com.example.enrollment.global.exception.BusinessException;
import com.example.enrollment.global.exception.ErrorCode;
import com.example.enrollment.global.exception.ErrorResponse;
import com.example.enrollment.global.security.UserPrincipal;
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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "2. Course", description = "강의 API - 강의 조회 및 등록")
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @Operation(
            summary = "강의 등록 🔒",
            description = "새로운 강의를 등록합니다. **강사(INSTRUCTOR) 권한이 필요합니다.** (Authorization 헤더에 Bearer 토큰 필요)",
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
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "강사만 등록 가능",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"code\":\"C003\",\"message\":\"강사만 강의를 등록할 수 있습니다\",\"timestamp\":\"2024-01-15T14:30:00\"}")
                    )
            )
    })
    @PostMapping
    public ResponseEntity<CourseDto.Response> createCourse(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody CourseDto.CreateRequest request) {
        if (userPrincipal.getRole() != Role.INSTRUCTOR) {
            throw new BusinessException(ErrorCode.INSTRUCTOR_ONLY);
        }
        CourseDto.Response response = courseService.createCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "강의 목록 조회",
            description = "등록된 모든 강의 목록을 조회합니다. 인증 없이 호출할 수 있습니다. 페이지네이션과 정렬을 지원합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = Page.class))
            )
    })
    @GetMapping
    public ResponseEntity<Page<CourseDto.ListResponse>> getAllCourses(
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "정렬 기준: recent (최신순), popular (신청자 많은 순), rate (신청률 높은 순)", example = "recent")
            @RequestParam(defaultValue = "recent") String sort
    ) {
        Page<CourseDto.ListResponse> courses = courseService.getAllCourses(page, size, sort);
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
