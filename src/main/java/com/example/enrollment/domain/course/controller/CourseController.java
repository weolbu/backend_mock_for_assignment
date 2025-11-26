package com.example.enrollment.domain.course.controller;

import com.example.enrollment.domain.course.dto.CourseDto;
import com.example.enrollment.domain.course.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseDto.Response> createCourse(@Valid @RequestBody CourseDto.CreateRequest request) {
        CourseDto.Response response = courseService.createCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CourseDto.ListResponse>> getAllCourses() {
        List<CourseDto.ListResponse> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDto.Response> getCourse(@PathVariable Long courseId) {
        CourseDto.Response response = courseService.getCourse(courseId);
        return ResponseEntity.ok(response);
    }
}
