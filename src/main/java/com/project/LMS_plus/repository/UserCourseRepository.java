package com.project.LMS_plus.repository;

import com.project.LMS_plus.entity.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {
    List<UserCourse> findByUser_StudentId(String studentId);

}
