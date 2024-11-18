package com.project.LMS_plus.controller;

import com.project.LMS_plus.dto.schoolcourse.CourseContentDto;
import com.project.LMS_plus.dto.schoolcourse.CourseDetailDTO;
import com.project.LMS_plus.entity.SchoolCourse;
import com.project.LMS_plus.service.SchoolCourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/school-courses")
@Tag(name = "SchoolCourse API", description = "과목 정보 관리 API")
public class SchoolCourseController {

    private final SchoolCourseService schoolCourseService;

    @Autowired
    public SchoolCourseController(SchoolCourseService schoolCourseService) {
        this.schoolCourseService = schoolCourseService;
    }

    // 모든 SchoolCourse 데이터를 반환하는 메서드
    @GetMapping
    public List<SchoolCourse> getAllSchoolCourse() {
        return schoolCourseService.getAllSchoolCourse();  // 서비스 메서드 호출
    }

    // 특정 courseId로 SchoolCourse를 조회하는 메서드
    @GetMapping("/{courseId}")
    public ResponseEntity<SchoolCourse> getSchoolCourseByCourseId(@PathVariable String courseId) {
        Optional<SchoolCourse> schoolCourse = schoolCourseService.getSchoolCourseByCourseId(courseId);

        if (schoolCourse.isPresent()) {
            return ResponseEntity.ok(schoolCourse.get());  // 존재하면 OK 반환
        } else {
            return ResponseEntity.notFound().build();  // 존재하지 않으면 404 Not Found 반환
        }
    }

    @GetMapping("/major/{majorPrefix}")
    public List<SchoolCourse> getCoursesByMajor(@PathVariable String majorPrefix) {
        return schoolCourseService.getCoursesByMajor(majorPrefix);
    }

}