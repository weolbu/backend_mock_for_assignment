package com.example.enrollment.domain.enrollment.service;

import com.example.enrollment.domain.course.entity.Course;
import com.example.enrollment.domain.course.repository.CourseRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
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

    @Transactional
    public EnrollmentDto.BatchResponse enrollBatch(Long userId, List<Long> courseIds) {
        User user = userService.findById(userId);

        List<EnrollmentDto.SuccessItem> successList = new ArrayList<>();
        List<EnrollmentDto.FailedItem> failedList = new ArrayList<>();

        for (Long courseId : courseIds) {
            // 강의 조회 (Optional 사용)
            Optional<Course> courseOpt = courseRepository.findById(courseId);
            if (courseOpt.isEmpty()) {
                failedList.add(new EnrollmentDto.FailedItem(courseId, ErrorCode.COURSE_NOT_FOUND.getMessage()));
                continue;
            }
            Course course = courseOpt.get();

            // 이미 수강 신청했는지 확인
            if (enrollmentRepository.existsByUserIdAndCourseId(userId, courseId)) {
                failedList.add(new EnrollmentDto.FailedItem(courseId, ErrorCode.ALREADY_ENROLLED.getMessage()));
                continue;
            }

            // 정원 확인
            if (course.isFull()) {
                failedList.add(new EnrollmentDto.FailedItem(courseId, ErrorCode.COURSE_FULL.getMessage()));
                continue;
            }

            // 수강 인원 증가
            course.enroll();

            // 수강 신청 저장
            Enrollment enrollment = Enrollment.builder()
                    .user(user)
                    .course(course)
                    .build();

            Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
            successList.add(new EnrollmentDto.SuccessItem(savedEnrollment));
        }

        return new EnrollmentDto.BatchResponse(successList, failedList);
    }
}
