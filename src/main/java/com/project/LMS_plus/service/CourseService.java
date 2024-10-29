package com.project.LMS_plus.service;

import com.opencsv.bean.CsvToBeanBuilder;
import com.project.LMS_plus.dto.CourseDetailDTO;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private static final String CSV_FILE_PATH = "C:\\Project\\RedPenLMS-BE\\src\\main\\resources\\csv\\game_software_courses.csv";

    public List<CourseDetailDTO> loadCourseDetails() {
        try (FileReader reader = new FileReader(CSV_FILE_PATH)) {
            return new CsvToBeanBuilder<CourseDetailDTO>(reader)
                    .withType(CourseDetailDTO.class)
                    .withSkipLines(1)  // 첫 번째 행(헤더)을 건너뛰도록 설정
                    .build()
                    .parse();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<CourseDetailDTO> searchBySubjectName(String subjectName) {
        List<CourseDetailDTO> allCourses = loadCourseDetails();
        if (allCourses == null) return null;

        return allCourses.stream()
                .filter(course -> course.getSubjectName().equalsIgnoreCase(subjectName))
                .collect(Collectors.toList());
    }
}
