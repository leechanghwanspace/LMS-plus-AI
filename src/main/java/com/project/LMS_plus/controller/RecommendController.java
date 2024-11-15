package com.project.LMS_plus.controller;

import com.project.LMS_plus.entity.Job;
import com.project.LMS_plus.entity.RecommendCourse;
import com.project.LMS_plus.service.RecommendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recommend")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Tag(name = "RecommendCourse Management", description = "강의 추천 API")
public class RecommendController {

    private final RecommendService recommendService;

    @Operation(summary = "CSV 파일 읽기", description = "지정된 경로에서 CSV 파일을 읽어 강의 데이터를 불러옵니다.")
    @GetMapping("/readCsv")
    public List<RecommendCourse> readCsv(
            @Parameter(description = "CSV 파일 경로", required = true)
            @RequestParam String filePath) {
        return recommendService.readCsv(filePath);
    }

    @Operation(summary = "특정 직무와 일치하는 강의 목록 조회", description = "지정된 직무와 일치하는 강의 목록을 반환합니다.")
    @GetMapping("/selectByJob")
    public List<RecommendCourse> selectingSameJob(
            @Parameter(description = "직무 ID", required = true)
            @RequestParam Long jobId,
            @RequestParam String filePath) {

        Job job = new Job();
        job.setId(jobId);  // 전달된 jobId를 사용해 Job 객체 생성
        List<RecommendCourse> courses = recommendService.readCsv(filePath);
        return recommendService.selectingSameJob(courses, job);
    }

    @Operation(summary = "특정 직무와 가장 높은 일치율을 가진 강의 추천", description = "지정된 직무와 가장 높은 일치율을 가진 추천 강의를 반환합니다.")
    @GetMapping("/recommendHighestMatch")
    public Optional<RecommendCourse> recommendHigherSameRate(
            @Parameter(description = "직무 ID", required = true)
            @RequestParam Long jobId,
            @RequestParam String filePath) {

        Job job = new Job();
        job.setId(jobId);  // 전달된 jobId를 사용해 Job 객체 생성
        List<RecommendCourse> courses = recommendService.readCsv(filePath);
        return recommendService.recommendHigherSameRate(courses, job);
    }
    @Operation(summary = "사용자 직무 기반 강의 목록 조회", description = "사용자가 설정한 직무에 맞는 강의 목록을 반환합니다.")
    @GetMapping("/coursesByUserJob")
    public List<RecommendCourse> getCoursesByUserJob(
            @Parameter(description = "사용자 ID", required = true)
            @RequestParam String studentId,
            @RequestParam String filePath) {

        List<RecommendCourse> courses = recommendService.readCsv(filePath);
        return recommendService.getCoursesByUserJob(studentId, courses);
    }

    @Operation(summary = "사용자 직무 기반 가장 높은 일치율 강의 추천", description = "사용자가 설정한 직무에 맞는 가장 높은 일치율을 가진 강의를 추천합니다.")
    @GetMapping("/topMatchCourseByUserJob")
    public Optional<RecommendCourse> getTopMatchCourseByUserJob(
            @Parameter(description = "사용자 ID", required = true)
            @RequestParam String studentId,
            @RequestParam String filePath) {

        List<RecommendCourse> courses = recommendService.readCsv(filePath);
        return recommendService.getTopMatchCourseByUserJob(studentId, courses);
    }
}
