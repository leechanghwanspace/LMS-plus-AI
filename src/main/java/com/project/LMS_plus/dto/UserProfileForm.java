package com.project.LMS_plus.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileForm {
    private Long departmentId;
    private String major;
    private String doubleMajor;
    private Integer year;
    private String jobRole; // 사용자가 선택한 직무(webapp,game,data,security) 필드 추가

    // Getters and setters
}
