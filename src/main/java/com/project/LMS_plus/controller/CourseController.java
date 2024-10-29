package com.project.LMS_plus.controller;

import com.project.LMS_plus.dto.CourseDetailDTO;
import com.project.LMS_plus.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // 1. 전체 과목 조회
    @GetMapping
    public List<CourseDetailDTO> getAllCourses() {
        return courseService.loadAllCourses();
    }

    // 2. 전공별 과목 조회
    @GetMapping("/{majorType}")
    public List<CourseDetailDTO> getCoursesByMajor(@PathVariable String majorType) {
        return courseService.loadCoursesByMajor(majorType);
    }

    // 3. 전공 내 세부 교과목 조회
    @GetMapping("/{majorType}/{subjectName}")
    public List<CourseDetailDTO> getCoursesBySubjectName(
            @PathVariable String majorType,
            @PathVariable String subjectName) {
        return courseService.searchCoursesBySubjectName(majorType, subjectName);
    }
}
