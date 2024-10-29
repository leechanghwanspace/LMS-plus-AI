package com.project.LMS_plus.controller;

import com.project.LMS_plus.dto.CourseDetailDTO;
import com.project.LMS_plus.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // 모든 과목 조회
    @GetMapping
    public List<CourseDetailDTO> getAllCourses() {
        return courseService.loadCourseDetails();
    }

    // 과목명으로 검색
    @GetMapping("/search/{subjectName}")
    public List<CourseDetailDTO> searchCoursesBySubjectName(@PathVariable String subjectName) {
        return courseService.searchBySubjectName(subjectName);
    }
}
