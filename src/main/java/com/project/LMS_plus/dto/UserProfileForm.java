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

    // Getters and setters
}
