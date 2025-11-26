package com.example.enrollment.domain.course.service;

import com.example.enrollment.domain.course.dto.CourseDto;
import com.example.enrollment.domain.course.entity.Course;
import com.example.enrollment.domain.course.repository.CourseRepository;
import com.example.enrollment.global.exception.BusinessException;
import com.example.enrollment.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;

    @Transactional
    public CourseDto.Response createCourse(CourseDto.CreateRequest request) {
        Course course = Course.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .instructorName(request.getInstructorName())
                .maxStudents(request.getMaxStudents())
                .build();

        Course savedCourse = courseRepository.save(course);
        return new CourseDto.Response(savedCourse);
    }

    public List<CourseDto.ListResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(CourseDto.ListResponse::new)
                .collect(Collectors.toList());
    }

    public CourseDto.Response getCourse(Long courseId) {
        Course course = findCourseById(courseId);
        return new CourseDto.Response(course);
    }

    public Course findCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COURSE_NOT_FOUND));
    }
}
