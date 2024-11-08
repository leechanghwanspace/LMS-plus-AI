package com.project.LMS_plus.controller;

import com.project.LMS_plus.dto.CourseDetailDTO;
import com.project.LMS_plus.dto.SchoolCourseDto;
import com.project.LMS_plus.service.SchoolCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/school-courses")
public class SchoolCourseController {

    private final SchoolCourseService schoolCourseService;

    @Autowired
    public SchoolCourseController(SchoolCourseService schoolCourseService) {
        this.schoolCourseService = schoolCourseService;
    }

    // 전체 과목 조회
    @GetMapping("/all")
    public ResponseEntity<List<CourseDetailDTO>> getAllCourses() {
        List<CourseDetailDTO> courses = schoolCourseService.loadAllCourses();
        return ResponseEntity.ok(courses);
    }

    // 전공별 과목 조회
    @GetMapping("/major/{majorType}")
    public ResponseEntity<List<CourseDetailDTO>> getCoursesByMajor(@PathVariable String majorType) {
        List<CourseDetailDTO> courses = schoolCourseService.loadCoursesByMajor(majorType);
        return ResponseEntity.ok(courses);
    }

    // 사용자가 과목을 저장
    @PostMapping("/save/{userId}")
    public ResponseEntity<String> saveSchoolCourse(@RequestBody SchoolCourseDto schoolCourseDto, @PathVariable String userId) {
        try {
            schoolCourseService.saveSchoolCourse(schoolCourseDto, userId);
            return ResponseEntity.ok("Course saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving course: " + e.getMessage());
        }
    }
}
