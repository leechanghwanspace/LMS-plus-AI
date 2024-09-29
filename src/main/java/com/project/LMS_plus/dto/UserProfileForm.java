package com.project.LMS_plus.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileForm {

    private String department;       // 소프트웨어공학부 여부에 따라 전공 선택
    private String major;            // 선택된 전공
    private String doubleMajor;      // 복수 전공
    private Integer year;            // 학년

    public UserProfileForm() {}

    public UserProfileForm(String department, String major, String doubleMajor, Integer year, String userStatus) {
        this.department = department;
        this.major = major;
        this.doubleMajor = doubleMajor;
        this.year = year;
    }
}
