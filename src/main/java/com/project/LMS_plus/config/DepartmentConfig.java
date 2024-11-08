package com.project.LMS_plus.config;

import com.project.LMS_plus.entity.Department;
import com.project.LMS_plus.repository.DepartmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DepartmentConfig {

    @Bean
    CommandLineRunner initDepartmentData(DepartmentRepository departmentRepository) {
        return args -> {
            // "소프트웨어공학부"라는 이름의 학부가 없을 때만 추가
            if (!departmentRepository.existsByName("소프트웨어공학부")) {
                Department department = new Department(
                        "소프트웨어공학부",
                        "소프트웨어공학부엔 게임소프트웨어학과, 정보보호학과, 스마트IT학과가 있습니다."
                );
                departmentRepository.save(department);
            }
        };
    }
}
