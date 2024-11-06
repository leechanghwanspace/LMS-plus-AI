package com.project.LMS_plus.service;

import com.project.LMS_plus.entity.Job;
import com.project.LMS_plus.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    // 사용자가 입력한 직무명을 저장하는 메서드
    public Job inputJobName(String jobName) {
        // 새로운 Job 객체 생성 및 직무명 설정
        Job job = new Job();
        job.setJobName(jobName);

        // Job 객체를 데이터베이스에 저장
        return jobRepository.save(job);
    }
}
