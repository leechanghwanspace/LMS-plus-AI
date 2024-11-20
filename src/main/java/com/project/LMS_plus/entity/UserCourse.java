package com.project.LMS_plus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "studentId", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "courseId", nullable = false)
    private SchoolCourse schoolCourse;




    // 기본 생성자
    public UserCourse() {}

    public UserCourse(User user, SchoolCourse schoolCourse) {
        this.user = user;
        this.schoolCourse = schoolCourse;

    }
}

