package com.project.LMS_plus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

    @Id
    private String studentId;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String major; // 주 전공

    @Column
    private String doubleMajor; // 복수 전공

    @Column
    private Integer year; // 학년

    // Department와의 1:1 관계 설정
    @OneToOne
    @JoinColumn(name = "department_id")  // User 테이블에 외래 키로 추가됨
    private Department department;

    // 기본 생성자
    public User() {}

    // 생성자
    public User(String studentId, String email, String password, String name, String major, String doubleMajor, Integer year, Department department) {
        this.studentId = studentId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.major = major;
        this.doubleMajor = doubleMajor;
        this.year = year;
        this.department = department;
    }
}
