package com.project.LMS_plus.dto;

import com.opencsv.bean.CsvBindByPosition;

public class CourseDetailDTO {
    @CsvBindByPosition(position = 0)
    private String subjectName;

    @CsvBindByPosition(position = 1)
    private int week;

    @CsvBindByPosition(position = 2)
    private String content;

    public CourseDetailDTO() {}

    public CourseDetailDTO(String subjectName, int week, String content) {
        this.subjectName = subjectName;
        this.week = week;
        this.content = content;
    }

    // Getters and toString() method
    public String getSubjectName() {
        return subjectName;
    }

    public int getWeek() {
        return week;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Subject: " + subjectName + ", Week: " + week + ", Content: " + content;
    }
}
