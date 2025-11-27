package com.example.enrollment.domain.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원 유형")
public enum Role {
    @Schema(description = "수강생")
    STUDENT,

    @Schema(description = "강사")
    INSTRUCTOR
}
