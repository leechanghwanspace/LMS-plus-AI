package com.project.LMS_plus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class SchoolCourse {

    @Id
    private String courseId;

    private String courseName;

    private int gradeScore;

    @OneToMany(mappedBy = "schoolCourse", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<SchoolCourseWeekContents> weekContents;  // 주차별 내용 리스트

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private User user;

    // 기본 생성자 및 기타 생성자들
    public SchoolCourse() {}

    public SchoolCourse(String courseId, String courseName, int gradeScore) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.gradeScore = gradeScore;
    }

    public void addWeekContent(SchoolCourseWeekContents weekContent) {
        if (this.weekContents == null) {
            this.weekContents = new ArrayList<>();
        }
        this.weekContents.add(weekContent);
        weekContent.setSchoolCourse(this);  // 부모와 자식 관계 설정
    }

    public void removeWeekContent(SchoolCourseWeekContents weekContent) {
        if (this.weekContents != null) {
            this.weekContents.remove(weekContent);
            weekContent.setSchoolCourse(null);  // 부모와 자식 관계 해제
        }
    }
}
