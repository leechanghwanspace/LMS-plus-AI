package com.project.LMS_plus.controller;

import com.project.LMS_plus.dto.schoolcourse.CourseContentDto;
import com.project.LMS_plus.dto.schoolcourse.CourseDetailDTO;
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

@RestController
@RequestMapping("/api/school-courses")
@Tag(name = "SchoolCourse API", description = "과목 정보 관리 API")
public class SchoolCourseController {

    private final SchoolCourseService schoolCourseService;

    @Autowired
    public SchoolCourseController(SchoolCourseService schoolCourseService) {
        this.schoolCourseService = schoolCourseService;
    }

    @Operation(summary = "전체 과목 조회", description = "모든 CSV 파일을 읽어와 모든 과목 데이터를 반환합니다.")
    @GetMapping("/all")
    public ResponseEntity<List<CourseDetailDTO>> getAllCourses() {
        List<CourseDetailDTO> courses = schoolCourseService.loadAllCourses();
        return ResponseEntity.ok(courses);
    }

    @Operation(summary = "전공별 과목 조회", description = "특정 전공의 CSV 파일을 읽어와 해당 전공의 과목 데이터를 반환합니다.")
    @GetMapping("/major/{majorType}")
    public ResponseEntity<List<CourseDetailDTO>> getCoursesByMajor(
            @Parameter(description = "조회할 전공의 이름", example = "ai") @PathVariable String majorType) {
        List<CourseDetailDTO> courses = schoolCourseService.loadCoursesByMajor(majorType);
        return ResponseEntity.ok(courses);
    }


    @Operation(summary = "전공별 과목 조회", description = "특정 전공의 CSV 파일을 읽어와 해당 전공의 과목 데이터를 반환합니다.")
    @GetMapping("/majorContent/{majorType}")
    public ResponseEntity<List<CourseContentDto>> getCoursesContentsByMajor(
            @Parameter(description = "조회할 전공의 이름", example = "ai") @PathVariable String majorType) {
        List<CourseContentDto> courses = schoolCourseService.loadCoursesContentsByMajor(majorType);
        return ResponseEntity.ok(courses);
    }
    @PostMapping("/register-batch/{studentId}")
    public ResponseEntity<String> registerCoursesBatch(@RequestBody List<CourseDetailDTO> courseDetails, @PathVariable String studentId) {
        try {
            if (courseDetails == null || courseDetails.isEmpty()) {
                return ResponseEntity.badRequest().body("과목 정보가 비어있습니다.");
            }


            // 서비스 호출하여 과목 저장
            schoolCourseService.saveSchoolCourse(studentId, courseDetails);

            return ResponseEntity.ok("모든 과목이 성공적으로 등록되었습니다.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("과목 등록 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}