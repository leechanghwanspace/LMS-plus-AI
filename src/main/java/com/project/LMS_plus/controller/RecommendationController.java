package com.project.LMS_plus.controller;

import com.project.LMS_plus.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/profile/recommendations")  // API 기본 경로
@Tag(name = "추천 API", description = "강의 추천 관련 기능을 제공합니다.")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping
    @Operation(summary = "직업 ID 기반 추천", description = "jobId를 기반으로 추천 강의 목록을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추천 목록 반환 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터")
    })
    public Map<String, Object> getProfileWithRecommendations(@RequestBody Map<String, Object> profileData) {
        int jobId = (int) profileData.get("jobId");

        List<Map<String, Object>> recommendedCourses = recommendationService.getRecommendedCourses(jobId);

        Map<String, Object> response = new HashMap<>(profileData);
        response.put("recommendedCourses", recommendedCourses);

        return response;
    }

    @PostMapping("/inflearn")
    @Operation(summary = "Inflearn 강의 추천", description = "courseName과 courseDetails를 기반으로 Inflearn 강의를 추천합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추천 목록 반환 성공"),
            @ApiResponse(responseCode = "400", description = "courseName과 courseDetails 값이 필요합니다.")
    })
    public ResponseEntity<List<Map<String, Object>>> recommendCourses(@RequestBody Map<String, String> request) {
        String courseName = request.getOrDefault("courseName", "").trim();
        String courseDetails = request.getOrDefault("courseDetails", "").trim();

        if (courseName.isEmpty() || courseDetails.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(List.of(Map.of("error", "Both 'courseName' and 'courseDetails' are required.")));
        }

        List<Map<String, Object>> recommendations = recommendationService.getRecommendedInflearnCourses(courseName, courseDetails);

        return ResponseEntity.ok(recommendations);
    }

    @PostMapping("/inflearn/random")
    @Operation(summary = "랜덤 Inflearn 강의 추천", description = "상위 추천 결과 중 3개의 강의를 랜덤으로 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "랜덤 추천 목록 반환 성공"),
            @ApiResponse(responseCode = "400", description = "courseName과 courseDetails 값이 필요합니다.")
    })
    public ResponseEntity<List<Map<String, Object>>> recommendRandomCourses(@RequestBody Map<String, String> request) {
        String courseName = request.getOrDefault("courseName", "").trim();
        String courseDetails = request.getOrDefault("courseDetails", "").trim();

        if (courseName.isEmpty() || courseDetails.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(List.of(Map.of("error", "Both 'courseName' and 'courseDetails' are required.")));
        }

        List<Map<String, Object>> recommendations = recommendationService.getRecommendedInflearnCourses(courseName, courseDetails);

        return ResponseEntity.ok(recommendations);
    }
}
