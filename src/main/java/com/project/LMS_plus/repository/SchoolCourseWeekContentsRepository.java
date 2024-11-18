package com.project.LMS_plus.repository;

import com.project.LMS_plus.entity.SchoolCourse;
import com.project.LMS_plus.entity.SchoolCourseWeekContents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolCourseWeekContentsRepository extends JpaRepository<SchoolCourseWeekContents, Long> {
    boolean existsBySchoolCourseAndWeek(SchoolCourse schoolCourse, int week);
    List<SchoolCourseWeekContents> findBySchoolCourse(SchoolCourse schoolCourse);

}
