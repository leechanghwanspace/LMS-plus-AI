package com.project.LMS_plus.repository;

import com.project.LMS_plus.dto.schoolcourse.CourseDetailDTO;
import com.project.LMS_plus.entity.SchoolCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SchoolCourseRepository extends JpaRepository<SchoolCourse, Long> {
    Optional<SchoolCourse> findByCourseId(String courseId);
    List<SchoolCourse> findByCourseIdStartingWith(String prefix);  // AI, GS, IS로 시작하는 courseId를 찾음

    Optional<SchoolCourse> findByCourseName(String courseName);  // 과목명으로 과목 조회

    List<SchoolCourse> findByUser_StudentId(String studentId);

}
