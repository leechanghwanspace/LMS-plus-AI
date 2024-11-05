package com.project.LMS_plus.service;

import com.opencsv.bean.CsvToBeanBuilder;
import com.project.LMS_plus.dto.CourseDetailDTO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private static final String BASE_CSV_PATH =
            "C:\\Project\\RedPenLMS-BE\\src\\main\\resources\\csv";

    // CSV 파일 로드 메서드
    private List<CourseDetailDTO> loadCourseFromFile(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            return new CsvToBeanBuilder<CourseDetailDTO>(reader)
                    .withType(CourseDetailDTO.class)
                    .withSkipLines(1)  // 첫 번째 행(헤더) 건너뛰기
                    .build()
                    .parse();
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();  // 예외 발생 시 빈 리스트 반환
        }
    }

    // 전체 과목 조회: 모든 CSV 파일의 데이터를 병합하여 반환
    public List<CourseDetailDTO> loadAllCourses() {
        List<CourseDetailDTO> allCourses = new ArrayList<>();
        File folder = new File(BASE_CSV_PATH);

        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.getName().endsWith(".csv")) {
                System.out.println("로드 중인 CSV 파일: " + file.getName());  // 디버깅 로깅 추가
                allCourses.addAll(loadCourseFromFile(file.getPath()));
            }
        }
        System.out.println("전체 로드된 과목 수: " + allCourses.size());
        return allCourses;
    }

    // 전공별 과목 조회: 특정 전공의 CSV 파일 로드
    public List<CourseDetailDTO> loadCoursesByMajor(String majorType) {
        String csvFilePath = BASE_CSV_PATH + majorType + "_courses_1.csv";  // 수정된 파일명
        System.out.println("전공별 CSV 파일 경로: " + csvFilePath);  // 디버깅 로깅 추가
        File csvFile = new File(csvFilePath);

        if (!csvFile.exists()) {
            System.err.println("CSV 파일을 찾을 수 없습니다: " + csvFilePath);
            return List.of();  // 빈 리스트 반환
        }

        return loadCourseFromFile(csvFilePath);
    }

    // 전공 내 세부 교과목 조회: 특정 전공에서 과목명을 기준으로 필터링
    public List<CourseDetailDTO> searchCoursesBySubjectName(String majorType, String subjectName) {
        List<CourseDetailDTO> majorCourses = loadCoursesByMajor(majorType);
        return majorCourses.stream()
                .filter(course -> course.getSubjectName().equalsIgnoreCase(subjectName))
                .collect(Collectors.toList());
    }

    // 직무에 맞는 과목 추천 메소드 추가
    public List<CourseDetailDTO> getRecommendedCourses(String majorType, String jobRole) {
        List<CourseDetailDTO> courses = loadCoursesByMajor(majorType);  // 수정: 파일명에 확장자를 포함하지 않음
        return courses.stream()
                .filter(course -> courseMatchesJobRole(course, jobRole))
                .collect(Collectors.toList());
    }

    private boolean courseMatchesJobRole(CourseDetailDTO course, String jobRole) {
        switch (jobRole.toLowerCase()) {
            case "webapp":
                return "True".equalsIgnoreCase(course.getWebapp());
            case "game":
                return "True".equalsIgnoreCase(course.getGame());
            case "data":
                return "True".equalsIgnoreCase(course.getData());
            case "security":
                return "True".equalsIgnoreCase(course.getSecurity());
            default:
                return false;
        }
    }
}