package com.project.LMS_plus.dto;

import com.opencsv.bean.CsvBindByPosition;

public class CourseDetailDTO {
    @CsvBindByPosition(position = 0)
    private String subjectCode;
    @CsvBindByPosition(position = 1)
    private String subjectName;
    @CsvBindByPosition(position = 2)
    private int credit;
    @CsvBindByPosition(position = 3)
    private String job1;
    @CsvBindByPosition(position = 4)
    private String job2;
    @CsvBindByPosition(position = 5)
    private String job3;
    @CsvBindByPosition(position = 6)
    private double rate;

    public CourseDetailDTO() {}

    public CourseDetailDTO(String subjectCode, String subjectName, int credit, String job1, String job2, String job3, double rate) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credit = credit;
        this.job1 = job1;
        this.job2 = job2;
        this.job3 = job3;
        this.rate = rate;
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

    public String getJob1() {
        return job1;
    }

    public void setJob1(String job1) {
        this.job1 = job1;
    }

    public String getJob2() {
        return job2;
    }

    public void setJob2(String job2) {
        this.job2 = job2;
    }

    public String getJob3() {
        return job3;
    }

    public void setJob3(String job3) {
        this.job3 = job3;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "CourseDetailDTO{" +
                "subjectCode='" + subjectCode + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", credit=" + credit +
                ", webapp='" + job1 + '\'' +
                ", game='" + job2 + '\'' +
                ", data='" + job3 + '\'' +
                ", security='" + rate + '\'' +
                '}';
    }
}