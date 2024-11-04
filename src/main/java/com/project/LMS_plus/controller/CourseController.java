package com.project.LMS_plus.controller;

import com.project.LMS_plus.dto.CourseDetailDTO;
import com.project.LMS_plus.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public List<CourseDetailDTO> getAllCourses() {
        return courseService.loadAllCourses();
    }

    // 전공별 과목 조회
    @GetMapping("/{majorType}")
    public List<CourseDetailDTO> getCoursesByMajor(@PathVariable String majorType) {
        return courseService.loadCoursesByMajor(majorType);
    }

    // 전공 내 세부 교과목 조회
    @GetMapping("/{majorType}/by-subject/{subjectName}")
    public List<CourseDetailDTO> getCoursesBySubjectName(
            @PathVariable String majorType, @PathVariable String subjectName) {
        return courseService.searchCoursesBySubjectName(majorType, subjectName);
    }

    // 전공 및 직무별 과목 조회
    @GetMapping("/{majorType}/by-job/{jobRole}")
    public List<CourseDetailDTO> getCoursesByMajorAndJobRole(
            @PathVariable String majorType, @PathVariable String jobRole) {
        return courseService.getRecommendedCourses(majorType, jobRole);
    }
}