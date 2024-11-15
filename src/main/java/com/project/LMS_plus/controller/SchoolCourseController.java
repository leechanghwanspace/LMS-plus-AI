package com.project.LMS_plus.controller;

import com.project.LMS_plus.dto.schoolcourse.CourseBatchRequest;
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

    @Operation(summary = "사용자 과목 저장", description = "사용자가 선택한 과목 정보를 저장합니다.")
    @PostMapping("/save/{studentId}")
    public ResponseEntity<String> saveSchoolCourse(
            @Parameter(description = "저장할 과목 정보") @RequestBody CourseDetailDTO courseDetailDTO,
            @Parameter(description = "사용자 ID", example = "91913505") @PathVariable String studentId) {
        try {
            schoolCourseService.saveSchoolCourse(courseDetailDTO, studentId);
            return ResponseEntity.ok("Course saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving course: " + e.getMessage());
        }
    }

    @Operation(summary = "전공별 과목 조회", description = "특정 전공의 CSV 파일을 읽어와 해당 전공의 과목 데이터를 반환합니다.")
    @GetMapping("/majorContent/{majorType}")
    public ResponseEntity<List<CourseContentDto>> getCoursesContentsByMajor(
            @Parameter(description = "조회할 전공의 이름", example = "ai") @PathVariable String majorType) {
        List<CourseContentDto> courses = schoolCourseService.loadCoursesContentsByMajor(majorType);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/majorContent/{majorType}/{courseName}")
    public ResponseEntity<List<CourseContentDto>> getCourseContentsByMajorAndCourseName(@PathVariable String majorType, @PathVariable String courseName) {
        List<CourseContentDto> courses = schoolCourseService.loadCoursesContentByMajorAndCourseName(majorType, courseName);
        return ResponseEntity.ok(courses);
    }


    @PostMapping("/register-batch")
    public ResponseEntity<String> registerCoursesBatch(@RequestBody CourseBatchRequest request) {
        try {
            String studentId = request.getStudentId();

            List<String> courseNames = request.getCourseName();
            List<Integer> courseNameList = request.getGradeScore();
            String majorName = request.getMajorName();

            if (courseNames.size() != courseNameList.size()) {
                return ResponseEntity.badRequest().body("과목과 학점의 개수가 일치하지 않습니다.");
            }

            for (int i = 0; i < courseNames.size(); i++) {
                schoolCourseService.saveSchoolCourseByNameWithGrade(
                        studentId, majorName, courseNames.get(i) , courseNameList.get(i)
                );
            }

            return ResponseEntity.ok("모든 과목이 성공적으로 등록되었습니다.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("과목 등록 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
