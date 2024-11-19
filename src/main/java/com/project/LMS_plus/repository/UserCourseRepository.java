package com.project.LMS_plus.repository;

import com.project.LMS_plus.entity.SchoolCourse;
import com.project.LMS_plus.entity.User;
import com.project.LMS_plus.entity.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {
    List<UserCourse> findByUser_StudentId(String studentId);
    List<UserCourse> findByUserAndSchoolCourse(User user, SchoolCourse schoolCourse);
    boolean existsBySchoolCourse(SchoolCourse schoolCourse);

}
