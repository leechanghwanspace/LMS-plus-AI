package com.project.LMS_plus.service;

import com.opencsv.bean.CsvToBeanBuilder;
import com.project.LMS_plus.dto.schoolcourse.CourseContentDto;
import com.project.LMS_plus.dto.schoolcourse.CourseDetailDTO;
import com.project.LMS_plus.entity.SchoolCourse;
import com.project.LMS_plus.entity.User;
import com.project.LMS_plus.repository.JobRepository;
import com.project.LMS_plus.repository.SchoolCourseRepository;
import com.project.LMS_plus.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SchoolCourseService {

    private final UserRepository userRepository;
    private final SchoolCourseRepository schoolCourseRepository;
    private final JobRepository jobRepository;

    @Autowired
    public SchoolCourseService(UserRepository userRepository, SchoolCourseRepository schoolCourseRepository, JobRepository jobRepository) {
        this.userRepository = userRepository;
        this.schoolCourseRepository = schoolCourseRepository;
        this.jobRepository = jobRepository;
    }

    private static final String BASE_CSV_PATH =
            "D:\\webproject\\RedPenLMS-BE\\src\\main\\resources\\csv";

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

    private List<CourseContentDto> loadCourseContentsFromFile(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            return new CsvToBeanBuilder<CourseContentDto>(reader)
                    .withType(CourseContentDto.class)
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
        String csvFilePath = BASE_CSV_PATH + "/"
                + majorType + "_courses_1.csv";  // 수정된 파일명
        System.out.println("전공별 CSV 파일 경로: " + csvFilePath);  // 디버깅 로깅 추가
        File csvFile = new File(csvFilePath);

        if (!csvFile.exists()) {
            System.err.println("CSV 파일을 찾을 수 없습니다: " + csvFilePath);
            return List.of();  // 빈 리스트 반환
        }

        return loadCourseFromFile(csvFilePath);
    }

    // 전공별 과목 조회: 특정 전공의 CSV 파일 로드
    public List<CourseContentDto> loadCoursesContentsByMajor(String majorType) {
        String csvFilePath = BASE_CSV_PATH + "/"
                + majorType + "_courses_2.csv";  // 수정된 파일명
        System.out.println("전공별 CSV 파일 경로: " + csvFilePath);  // 디버깅 로깅 추가
        File csvFile = new File(csvFilePath);

        if (!csvFile.exists()) {
            System.err.println("CSV 파일을 찾을 수 없습니다: " + csvFilePath);
            return List.of();  // 빈 리스트 반환
        }

        return loadCourseContentsFromFile(csvFilePath);
    }

    public List<CourseContentDto> loadCoursesContentByMajorAndCourseName(String majorType, String courseName) {
        String csvFilePath = BASE_CSV_PATH + "/" + majorType + "_courses_2.csv";  // 수정된 파일명
        System.out.println("전공별 CSV 파일 경로: " + csvFilePath);  // 디버깅 로깅 추가
        File csvFile = new File(csvFilePath);

        if (!csvFile.exists()) {
            System.err.println("CSV 파일을 찾을 수 없습니다: " + csvFilePath);
            return List.of();  // 빈 리스트 반환
        }

        try (FileReader reader = new FileReader(csvFile)) {
            // OpenCSV를 사용하여 CSV 파일을 CourseContentDto 객체로 매핑
            List<CourseContentDto> courses = new CsvToBeanBuilder<CourseContentDto>(reader)
                    .withType(CourseContentDto.class)  // CSV 데이터를 CourseContentDto 객체로 변환
                    .withSkipLines(1)
                    .build()
                    .parse();

            // courseName에 맞는 데이터 필터링
            return courses.stream()
                    .filter(course -> course.getSubjectName().equalsIgnoreCase(courseName))  // 대소문자 구분 없이 검색
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("CSV 파일을 읽는 중 오류가 발생했습니다: " + e.getMessage());
            return List.of();  // 빈 리스트 반환
        }
    }

    public void saveSchoolCourse(String studentId, List<CourseDetailDTO> courseDetails) {
        // 사용자 조회
        User user = userRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + studentId));

        // 과목 정보 저장
        for (CourseDetailDTO courseDetail : courseDetails) {
            SchoolCourse schoolCourse = new SchoolCourse();
            schoolCourse.setCourseId(courseDetail.getSubjectCode());
            schoolCourse.setCourseName(courseDetail.getSubjectName());
            schoolCourse.setGradeScore(courseDetail.getCredit()); // 학점 정보를 GradeScore에 설정
            schoolCourse.setJob1(courseDetail.getJob1());
            schoolCourse.setJob2(courseDetail.getJob2());
            schoolCourse.setJob3(courseDetail.getJob3());
            schoolCourse.setCorrectRate(courseDetail.getRate());
            schoolCourse.setUser(user);

            // 과목 저장
            schoolCourseRepository.save(schoolCourse);
        }
    }
}