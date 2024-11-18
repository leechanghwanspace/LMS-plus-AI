package com.project.LMS_plus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
public class SchoolCourse {

    @Id
    private String courseId;

    @Column(nullable = false)
    private String courseName;

    @Column(length = 5000)
    private String courseDetails;

    @Column(nullable = false)
    private int gradeScore;


    @OneToMany(mappedBy = "schoolCourse", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<SchoolCourseWeekContents> weekContents = new ArrayList<>();

    // 기본 생성자
    public SchoolCourse() {}

    public SchoolCourse(String courseId, String courseName, int gradeScore) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.gradeScore = gradeScore;
    }

    public void addWeekContent(SchoolCourseWeekContents weekContent) {
        weekContents.add(weekContent);
        weekContent.setSchoolCourse(this);
    }

    public void removeWeekContent(SchoolCourseWeekContents weekContent) {
        weekContents.remove(weekContent);
        weekContent.setSchoolCourse(null);
    }
}

