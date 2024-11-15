package com.project.LMS_plus.dto;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CourseContentDto {

    @CsvBindByPosition(position = 0)
    private String subjectCode;
    @CsvBindByPosition(position = 1)
    private String subjectName;
    @CsvBindByPosition(position = 2)
    private int credit;
    @CsvBindByPosition(position = 3)
    private String weeklyContent;

    public CourseContentDto() {}

    public CourseContentDto(String subjectCode, String subjectName, int credit, String weeklyContent) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credit = credit;
        this.weeklyContent = weeklyContent;
    }

    @Override
    public String toString() {
        return "CourseContentDto{" +
                "subjectCode='" + subjectCode + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", credit=" + credit +
                ", weeklyContent='" + weeklyContent + '\'' +
                '}';
    }
}
