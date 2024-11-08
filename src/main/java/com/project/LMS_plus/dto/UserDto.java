package com.project.LMS_plus.dto;

import com.project.LMS_plus.entity.Job;
import com.project.LMS_plus.entity.SchoolCourse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserDto {
    private String studentId;
    private String email;
    private String name;
    private String major;
    private Integer year;
    private String departmentName;  // Department 정보
    private Job job;
    private List<SchoolCourse> schoolCourses;

    // 생성자, getter, setter 추가
    public UserDto(String studentId, String email, String name, String major, Integer year, String departmentName, Job job) {
        this.studentId = studentId;
        this.email = email;
        this.name = name;
        this.major = major;
        this.year = year;
        this.departmentName = departmentName;
        this.job = job;
    }


    public UserDto(String studentId, String email, String name, String major, Integer year, String departmentName, Job job, List<SchoolCourse> schoolCourses) {
        this.studentId = studentId;
        this.email = email;
        this.name = name;
        this.major = major;
        this.year = year;
        this.departmentName = departmentName;
        this.job = job;
        this.schoolCourses = schoolCourses;
    }
}
