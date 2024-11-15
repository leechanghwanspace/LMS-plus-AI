package com.project.LMS_plus.dto.schoolcourse;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CourseBatchRequest {

    private String studentId;
    private String majorName;
    private List<String> courseName;
    private List<Integer> gradeScore;
}
