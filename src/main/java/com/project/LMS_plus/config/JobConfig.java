package com.project.LMS_plus.config;

import com.project.LMS_plus.entity.Job;
import com.project.LMS_plus.repository.JobRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class JobConfig {

    @Bean
    CommandLineRunner initJobData(JobRepository jobRepository) {
        return args -> {
            List<String> jobNames = List.of(
                    "게임개발자", "게임 기획자", "VR/AR 개발자", "웹 개발자", "앱 개발자", "데이터 엔지니어",
                    "데브옵스 엔지니어", "네트워크 엔지니어", "보안 엔지니어", "사이버 침해 대응", "디지털 포렌식",
                    "클라우드 보안", "IoT 보안", "정보보안 전문가", "IoT", "데이터 분석", "인공지능", "스마트 팩토리",
                    "자동화 시스템", "모바일 앱 개발", "스마트 기술", "블록체인"
            );

            for (String name : jobNames) {
                jobRepository.save(new Job(name));
            }
        };
    }
}
