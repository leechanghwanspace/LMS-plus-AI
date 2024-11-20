package com.project.LMS_plus.dto.schoolcourse;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CourseContentDto {

    @CsvBindByPosition(position = 0)
    private String courseId;
    @CsvBindByPosition(position = 1)
    private String courseName;
    @CsvBindByPosition(position = 2)
    private int week;
    @CsvBindByPosition(position = 3)
    private String weeklyContent;

    public CourseContentDto() {}

    public CourseContentDto(String courseId, String courseName, int week, String weeklyContent) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.week = week;
        this.weeklyContent = weeklyContent;
    }

    @Override
    public String toString() {
        return "CourseContentDto{" +
                "courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", week=" + week +
                ", weeklyContent='" + weeklyContent + '\'' +
                '}';
    }
}
