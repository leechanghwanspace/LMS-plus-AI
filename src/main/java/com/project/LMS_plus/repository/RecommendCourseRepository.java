package com.project.LMS_plus.repository;

import com.project.LMS_plus.entity.RecommendCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendCourseRepository extends JpaRepository<RecommendCourse, Long> {
}
