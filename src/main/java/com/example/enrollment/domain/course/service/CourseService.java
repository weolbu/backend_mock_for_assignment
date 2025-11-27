package com.example.enrollment.domain.course.service;

import com.example.enrollment.domain.course.dto.CourseDto;
import com.example.enrollment.domain.course.entity.Course;
import com.example.enrollment.domain.course.repository.CourseRepository;
import com.example.enrollment.global.exception.BusinessException;
import com.example.enrollment.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .price(request.getPrice())
                .build();

        Course savedCourse = courseRepository.save(course);
        return new CourseDto.Response(savedCourse);
    }

    public Page<CourseDto.ListResponse> getAllCourses(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Course> coursePage;
        switch (sort) {
            case "popular":
                coursePage = courseRepository.findAllByOrderByCurrentStudentsDesc(pageable);
                break;
            case "rate":
                coursePage = courseRepository.findAllOrderByEnrollmentRateDesc(pageable);
                break;
            case "recent":
            default:
                coursePage = courseRepository.findAllByOrderByCreatedAtDesc(pageable);
                break;
        }

        return coursePage.map(CourseDto.ListResponse::new);
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
