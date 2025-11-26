package com.example.enrollment.domain.enrollment.controller;

import com.example.enrollment.domain.enrollment.dto.EnrollmentDto;
import com.example.enrollment.domain.enrollment.service.EnrollmentService;
import com.example.enrollment.global.exception.ErrorResponse;
import com.example.enrollment.global.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "3. Enrollment", description = "수강 신청 API - 강의 수강 신청")
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Operation(
            summary = "수강 신청 🔒",
            description = """
                    강의에 수강 신청합니다. **인증이 필요합니다.**

                    ### 사용 방법
                    1. 먼저 로그인 API를 호출하여 JWT 토큰을 발급받으세요.
                    2. 우측 상단의 **Authorize** 버튼을 클릭하세요.
                    3. 발급받은 토큰을 입력하세요 (Bearer 제외).
                    4. 수강하려는 강의의 ID를 입력하고 실행하세요.

                    ### 주의사항
                    - 이미 수강 신청한 강의는 다시 신청할 수 없습니다.
                    - 정원이 가득 찬 강의는 수강 신청할 수 없습니다.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "수강 신청 성공",
                    content = @Content(schema = @Schema(implementation = EnrollmentDto.Response.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "정원 초과",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"code\":\"C002\",\"message\":\"수강 정원이 초과되었습니다\",\"timestamp\":\"2024-01-15T14:30:00\"}")
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
                    responseCode = "404",
                    description = "강의 없음",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"code\":\"C001\",\"message\":\"강의를 찾을 수 없습니다\",\"timestamp\":\"2024-01-15T14:30:00\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "이미 수강 신청함",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"code\":\"E001\",\"message\":\"이미 수강 신청한 강의입니다\",\"timestamp\":\"2024-01-15T14:30:00\"}")
                    )
            )
    })
    @PostMapping("/{courseId}/enroll")
    public ResponseEntity<EnrollmentDto.Response> enroll(
            @Parameter(description = "강의 ID", example = "1")
            @PathVariable Long courseId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        EnrollmentDto.Response response = enrollmentService.enroll(userPrincipal.getUserId(), courseId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
