package com.project.LMS_plus.config;

import com.project.LMS_plus.service.SchoolCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchoolCourseConfig {

    @Autowired
    private SchoolCourseService schoolCourseService;  // CSV 로딩과 데이터 저장 로직이 구현된 서비스

    @Bean
    public ApplicationRunner loadInitialData() {
        return args -> {
            String[] majorTypes = {"ai", "game_software", "information_security"};  // 예시로 주요 전공을 배열로 정의 (필요한 전공만 추가)
            for (String majorType : majorTypes) {
                schoolCourseService.saveCoursesFromCSVToDatabase(majorType);
            }
        };
    }

    @Bean
    public ApplicationRunner loadData() {
        return args -> {
            // 주요 전공 이름을 배열로 정의하거나 필요한 전공만 처리
            String[] majorTypes = {"ai", "game_software", "information_security"}; // 예시 전공 타입

            for (String majorType : majorTypes) {
                schoolCourseService.saveCourseContentsFromCSV(majorType); // CSV 데이터를 로드하여 저장하는 서비스 호출
            }
        };
    }
}
