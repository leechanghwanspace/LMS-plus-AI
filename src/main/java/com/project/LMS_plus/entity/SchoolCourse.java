package com.project.LMS_plus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SchoolCourse {

    @Id
    private String courseId;

    private String courseName;


    private int gradeScore;


    @ManyToOne
    @JoinColumn(name = "student_id")  // User와의 관계 설정
    @JsonIgnore  // user 정보는 JSON 응답에서 제외
    private User user;  // 과목을 수강한 사용자
}
