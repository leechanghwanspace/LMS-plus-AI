package com.project.LMS_plus.controller;

import com.project.LMS_plus.entity.SchoolCourse;
import com.project.LMS_plus.service.SchoolCourseService;
import com.project.LMS_plus.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/school-courses")
@Tag(name = "SchoolCourse API", description = "과목 정보 관리 API")
public class SchoolCourseController {

    private final SchoolCourseService schoolCourseService;
    private final UserService userService;

    @Autowired
    public SchoolCourseController(SchoolCourseService schoolCourseService, UserService userService) {
        this.schoolCourseService = schoolCourseService;
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "모든 과목 조회", description = "모든 SchoolCourse 데이터를 반환합니다.")
    public List<SchoolCourse> getAllSchoolCourse() {
        return schoolCourseService.getAllSchoolCourse();
    }

    @GetMapping("/{courseId}")
    @Operation(summary = "특정 과목 조회", description = "courseId를 사용하여 특정 SchoolCourse 데이터를 반환합니다.")
    public ResponseEntity<SchoolCourse> getSchoolCourseByCourseId(
            @Parameter(description = "조회할 과목의 ID", example = "CS101") @PathVariable String courseId) {
        Optional<SchoolCourse> schoolCourse = schoolCourseService.getSchoolCourseByCourseId(courseId);

        if (schoolCourse.isPresent()) {
            return ResponseEntity.ok(schoolCourse.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/major/{majorPrefix}")
    @Operation(summary = "전공 기반 과목 조회", description = "전공 접두어(majorPrefix)를 기준으로 과목 목록을 반환합니다.")
    public List<SchoolCourse> getCoursesByMajor(
            @Parameter(description = "전공 접두어 (예: CS, IT)", example = "CS") @PathVariable String majorPrefix) {
        return schoolCourseService.getCoursesByMajor(majorPrefix);
    }

    @GetMapping("/{studentId}/course-info")
    @Operation(summary = "학생의 모든 과목 정보 조회", description = "특정 학생 ID에 해당하는 모든 과목 정보를 반환합니다.")
    public ResponseEntity<List<Map<String, String>>> getUserCourseInfo(
            @Parameter(description = "학생 ID", example = "2023101234") @PathVariable String studentId) {
        List<Map<String, String>> courseInfoList = userService.loadUserSchoolCourseNameAndDetails(studentId);
        return ResponseEntity.ok(courseInfoList);
    }

    @GetMapping("/{studentId}/course-info/{courseId}")
    @Operation(summary = "학생의 특정 과목 정보 조회", description = "특정 학생 ID와 과목 ID에 해당하는 과목 정보를 반환합니다.")
    public ResponseEntity<Map<String, String>> getUserCourseInfoByCourseId(
            @Parameter(description = "학생 ID", example = "2023101234") @PathVariable String studentId,
            @Parameter(description = "과목 ID", example = "CS101") @PathVariable String courseId) {
        Map<String, String> courseInfo = userService.loadUserSchoolCourseNameAndDetailsByCourseId(studentId, courseId);
        return ResponseEntity.ok(courseInfo);
    }
}
