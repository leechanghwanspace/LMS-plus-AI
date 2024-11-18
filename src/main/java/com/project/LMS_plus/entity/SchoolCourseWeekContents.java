package com.project.LMS_plus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SchoolCourseWeekContents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 기본 키로 사용될 자동 생성 ID

    private int week;  // 주차 번호
    private String weeklyContent;  // 주차별 학습 내용

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "courseId")
    private SchoolCourse schoolCourse;  // 부모 엔티티와의 관계 설정
}
