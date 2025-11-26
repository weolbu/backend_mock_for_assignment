package com.example.enrollment.domain.course.controller;

import com.example.enrollment.domain.course.dto.CourseDto;
import com.example.enrollment.domain.course.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Course", description = "강의 API")
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @Operation(summary = "강의 등록", description = "새로운 강의를 등록합니다. (인증 필요)")
    @PostMapping
    public ResponseEntity<CourseDto.Response> createCourse(@Valid @RequestBody CourseDto.CreateRequest request) {
        CourseDto.Response response = courseService.createCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "강의 목록 조회", description = "등록된 모든 강의 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<CourseDto.ListResponse>> getAllCourses() {
        List<CourseDto.ListResponse> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @Operation(summary = "강의 상세 조회", description = "특정 강의의 상세 정보를 조회합니다.")
    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDto.Response> getCourse(@PathVariable Long courseId) {
        CourseDto.Response response = courseService.getCourse(courseId);
        return ResponseEntity.ok(response);
    }
}
