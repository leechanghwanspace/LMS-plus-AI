package com.project.LMS_plus.dto;

import com.project.LMS_plus.entity.Job;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {
    private String studentId;
    private String email;
    private String name;
    private String major;
    private String doubleMajor;
    private Integer year;
    private String departmentName;  // Department 정보
    private Job job;

    // 생성자, getter, setter 추가
    public UserDto(String studentId, String email, String name, String major, String doubleMajor, Integer year, String departmentName,Job job) {
        this.studentId = studentId;
        this.email = email;
        this.name = name;
        this.major = major;
        this.doubleMajor = doubleMajor;
        this.year = year;
        this.departmentName = departmentName;
        this.job = job;
    }
}
