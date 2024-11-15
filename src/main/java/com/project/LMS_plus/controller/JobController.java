package com.project.LMS_plus.controller;

import com.project.LMS_plus.entity.Job;
import com.project.LMS_plus.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
@Tag(name = "Job Management", description = "직무 정보를 관리하는 API")
public class JobController {

    private final JobService jobService;

    @Operation(summary = "단일 직무 저장", description = "직무 이름을 입력받아 단일 직무 정보를 저장합니다.")
    @PostMapping("/save")
    public ResponseEntity<Job> saveJob(
            @Parameter(description = "저장할 직무 이름", required = true)
            @RequestParam String jobName) {
        Job savedJob = jobService.inputJobName(jobName);
        return ResponseEntity.ok(savedJob);
    }

    @Operation(summary = "여러 직무 저장", description = "직무 이름의 목록을 입력받아 여러 직무를 저장합니다.")
    @PostMapping("/save-multiple")
    public ResponseEntity<String> saveMultipleJobs(
            @Parameter(description = "저장할 직무 이름 목록", required = true)
            @RequestBody List<String> jobNames) {
        for (String jobName : jobNames) {
            jobService.inputJobName(jobName);
        }
        return ResponseEntity.ok("여러 직무가 성공적으로 저장되었습니다");
    }
}
