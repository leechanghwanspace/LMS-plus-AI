package com.project.LMS_plus.py_recommendation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/profile/recommendations")  // 경로를 /api/profile/recommendations로 변경
public class JobRecommendationController {

    private final JobRecommendationService jobRecommendationService;

    @Autowired
    public JobRecommendationController(JobRecommendationService jobRecommendationService) {
        this.jobRecommendationService = jobRecommendationService;
    }

    @PostMapping
    public Map<String, Object> getProfileWithRecommendations(@RequestBody Map<String, Object> profileData) {
        int jobId = (int) profileData.get("jobId");

        List<Map<String, Object>> recommendedCourses = jobRecommendationService.getRecommendedCourses(jobId);

        Map<String, Object> response = new HashMap<>(profileData);
        response.put("recommendedCourses", recommendedCourses);

        return response;
    }
}
