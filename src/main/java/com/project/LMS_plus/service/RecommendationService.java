package com.project.LMS_plus.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class RecommendationService {

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

    public List<Map<String, Object>> getRecommendedInflearnCourses(String courseName, String courseDetails) {
        String url = "http://localhost:5000/recommend";
        RestTemplate restTemplate = new RestTemplate();

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 바디 생성
        Map<String, String> requestBody = Map.of(
                "courseName", courseName,
                "courseDetails", courseDetails
        );

        // HttpEntity 생성
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Flask 서버에 POST 요청 전송
        ResponseEntity<List> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                List.class
        );

        // 응답 반환
        return response.getBody();
    }
    public List<Map<String, Object>> getRecommendedRandomInflearnCourses(String courseName, String courseDetails) {
        String url = "http://localhost:5000/recommend/random";
        RestTemplate restTemplate = new RestTemplate();

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 바디 생성
        Map<String, String> requestBody = Map.of(
                "courseName", courseName,
                "courseDetails", courseDetails
        );

        // HttpEntity 생성
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Flask 서버에 POST 요청 전송
        ResponseEntity<List> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                List.class
        );

        // 응답 반환
        return response.getBody();
    }}
