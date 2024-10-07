package com.project.LMS_plus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

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

    @OneToOne
    @JoinColumn(name = "major_department_id")  // 외래 키로 사용
    private Department major;                  // 주 전공

    @OneToOne
    @JoinColumn(name = "double_major_department_id")  // 외래 키로 사용
    private Department doubleMajor;                    // 복수 전공

    @Column
    private Integer year;            // 학년

}