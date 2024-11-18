package com.project.LMS_plus.dto;

import com.project.LMS_plus.entity.Job;
import com.project.LMS_plus.entity.SchoolCourse;
import com.project.LMS_plus.entity.UserCourse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

import java.util.List;

@Getter
@Setter
public class UserDto {

    private String studentId;
    private String email;
    private String name;
    private String major;
    private Integer year;
    private String departmentName;
    private Job job;
    private List<UserCourse> userCourses; // UserCourse 리스트로 변경

    public UserDto(String studentId, String email, String name, String major, Integer year,
                   String departmentName, Job job, List<UserCourse> userCourses) {
        this.studentId = studentId;
        this.email = email;
        this.name = name;
        this.major = major;
        this.year = year;
        this.departmentName = departmentName;
        this.job = job;
        this.userCourses = userCourses;
    }
}

