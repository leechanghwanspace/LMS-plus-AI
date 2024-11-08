package com.project.LMS_plus.dto;

import com.opencsv.bean.CsvBindByPosition;

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

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getWeeklyContent() {
        return weeklyContent;
    }

    public void setWeeklyContent(String weeklyContent) {
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
