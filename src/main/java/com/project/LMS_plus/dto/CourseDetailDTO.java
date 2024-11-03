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
    private String webapp;  // boolean에서 String으로 변경
    @CsvBindByPosition(position = 4)
    private String game;    // boolean에서 String으로 변경
    @CsvBindByPosition(position = 5)
    private String data;    // boolean에서 String으로 변경
    @CsvBindByPosition(position = 6)
    private String security; // boolean에서 String으로 변경

    public CourseDetailDTO() {}

    public CourseDetailDTO(String subjectCode, String subjectName, int credit, String webapp, String game, String data, String security) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credit = credit;
        this.webapp = webapp;
        this.game = game;
        this.data = data;
        this.security = security;
    }

    // Getters
    public String getSubjectCode() {
        return subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getCredit() {
        return credit;
    }

    public String getWebapp() {
        return webapp;
    }

    public String getGame() {
        return game;
    }

    public String getData() {
        return data;
    }

    public String getSecurity() {
        return security;
    }

    @Override
    public String toString() {
        return "CourseDetailDTO{" +
                "subjectCode='" + subjectCode + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", credit=" + credit +
                ", webapp='" + webapp + '\'' +
                ", game='" + game + '\'' +
                ", data='" + data + '\'' +
                ", security='" + security + '\'' +
                '}';
    }
}