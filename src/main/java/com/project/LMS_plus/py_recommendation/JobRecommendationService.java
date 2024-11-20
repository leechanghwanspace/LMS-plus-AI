package com.project.LMS_plus.py_recommendation;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import java.util.List;
import java.util.Map;

@Service
public class JobRecommendationService {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Map<String, Object>> getRecommendedCourses(int jobId) {
        String url = "http://localhost:5000/recommend-courses";

        // 요청 바디에 job_id 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        Map<String, Integer> body = Map.of("job_id", jobId);
        HttpEntity<Map<String, Integer>> requestEntity = new HttpEntity<>(body, headers);

        // Flask 서버에 요청 전송
        ResponseEntity<List> response = restTemplate.postForEntity(url, requestEntity, List.class);

        return response.getBody();
    }
}
