package com.example.enrollment.domain.course.entity;

import com.example.enrollment.global.exception.BusinessException;
import com.example.enrollment.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "course")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "instructor_name", nullable = false)
    private String instructorName;

    @Column(name = "max_students", nullable = false)
    private Integer maxStudents;

    @Column(name = "current_students", nullable = false)
    private Integer currentStudents = 0;

    @Column(nullable = false)
    private Integer price;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Course(String title, String description, String instructorName, Integer maxStudents, Integer price) {
        this.title = title;
        this.description = description;
        this.instructorName = instructorName;
        this.maxStudents = maxStudents;
        this.currentStudents = 0;
        this.price = price;
        this.createdAt = LocalDateTime.now();
    }

    public void enroll() {
        if (this.currentStudents >= this.maxStudents) {
            throw new BusinessException(ErrorCode.COURSE_FULL);
        }
        this.currentStudents++;
    }

    public boolean isFull() {
        return this.currentStudents >= this.maxStudents;
    }

    public int getAvailableSeats() {
        return this.maxStudents - this.currentStudents;
    }
}
