package com.project.LMS_plus.dto;

import com.project.LMS_plus.entity.Job;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SchoolCourseDto {

    private String courseId;

    private String courseName;

    private Long jobId;

    private int gradeScore;

    private double correctRate;

}
