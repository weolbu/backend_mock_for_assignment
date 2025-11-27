package com.example.enrollment.domain.course.repository;

import com.example.enrollment.domain.course.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseRepository extends JpaRepository<Course, Long> {

    // 최신순 정렬
    Page<Course> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // 신청자 많은 순 정렬
    Page<Course> findAllByOrderByCurrentStudentsDesc(Pageable pageable);

    // 신청률 높은 순 정렬 (currentStudents / maxStudents)
    @Query("SELECT c FROM Course c ORDER BY (c.currentStudents * 1.0 / c.maxStudents) DESC")
    Page<Course> findAllOrderByEnrollmentRateDesc(Pageable pageable);
}
