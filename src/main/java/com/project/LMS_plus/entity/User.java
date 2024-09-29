package com.project.LMS_plus.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class User {
    @Id
    private String studentId;
    private String email;
    private String password;
    private String name;
    private String department;       // 학과 정보
    private String major;            // 전공
    private String doubleMajor;      // 복수 전공
    private Integer year;            // 학년

}