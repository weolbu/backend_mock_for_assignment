package com.example.enrollment.domain.enrollment.service;

import com.example.enrollment.domain.course.entity.Course;
import com.example.enrollment.domain.course.service.CourseService;
import com.example.enrollment.domain.enrollment.dto.EnrollmentDto;
import com.example.enrollment.domain.enrollment.entity.Enrollment;
import com.example.enrollment.domain.enrollment.repository.EnrollmentRepository;
import com.example.enrollment.domain.user.entity.User;
import com.example.enrollment.domain.user.service.UserService;
import com.example.enrollment.global.exception.BusinessException;
import com.example.enrollment.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseService courseService;
    private final UserService userService;

    @Transactional
    public EnrollmentDto.Response enroll(Long userId, Long courseId) {
        // 사용자 확인
        User user = userService.findById(userId);

        // 강의 확인
        Course course = courseService.findCourseById(courseId);

        // 이미 수강 신청했는지 확인
        if (enrollmentRepository.existsByUserIdAndCourseId(userId, courseId)) {
            throw new BusinessException(ErrorCode.ALREADY_ENROLLED);
        }

        // 수강 인원 증가 (내부에서 정원 초과 체크)
        course.enroll();

        // 수강 신청 저장
        Enrollment enrollment = Enrollment.builder()
                .user(user)
                .course(course)
                .build();

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        return new EnrollmentDto.Response(savedEnrollment);
    }
}
