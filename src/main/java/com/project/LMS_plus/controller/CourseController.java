package com.project.LMS_plus.controller;

import com.project.LMS_plus.dto.CourseDetailDTO;
import com.project.LMS_plus.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

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
