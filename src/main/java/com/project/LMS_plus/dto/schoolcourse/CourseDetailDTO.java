package com.project.LMS_plus.dto.schoolcourse;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CourseDetailDTO {
    @CsvBindByPosition(position = 0)
    private String courseId;
    @CsvBindByPosition(position = 1)
    private String courseName;
    @CsvBindByPosition(position = 2)
    private int gradeScore;


    public CourseDetailDTO() {}

    public CourseDetailDTO(String courseId, String courseName, int gradeScore) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.gradeScore = gradeScore;

    }

    @Override
    public String toString() {
        return "CourseDetailDTO{" +
                "courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", gradeScore=" + gradeScore +
                '}';
    }

}