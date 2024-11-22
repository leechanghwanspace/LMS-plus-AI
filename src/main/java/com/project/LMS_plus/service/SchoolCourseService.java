package com.project.LMS_plus.service;

import com.opencsv.bean.CsvToBeanBuilder;
import com.project.LMS_plus.dto.schoolcourse.CourseContentDto;
import com.project.LMS_plus.dto.schoolcourse.CourseDetailDTO;
import com.project.LMS_plus.entity.SchoolCourse;
import com.project.LMS_plus.entity.SchoolCourseWeekContents;
import com.project.LMS_plus.entity.User;
import com.project.LMS_plus.entity.UserCourse;
import com.project.LMS_plus.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
public class SchoolCourseService {

    private final UserRepository userRepository;
    private final SchoolCourseRepository schoolCourseRepository;
    private final SchoolCourseWeekContentsRepository schoolCourseWeekContentsRepository;
    private final UserCourseRepository userCourseRepository;


    @Autowired
    public SchoolCourseService(UserRepository userRepository, SchoolCourseRepository schoolCourseRepository, SchoolCourseWeekContentsRepository schoolCourseWeekContentsRepository, UserCourseRepository userCourseRepository) {
        this.userRepository = userRepository;
        this.schoolCourseRepository = schoolCourseRepository;
        this.schoolCourseWeekContentsRepository = schoolCourseWeekContentsRepository;
        this.userCourseRepository = userCourseRepository;
    }

    private static final String BASE_CSV_PATH =
            "src/main/resources/csv";

    // CSV 파일 로드 메서드
    private Set<CourseDetailDTO> loadCourseFromFile(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            return new HashSet<>(new CsvToBeanBuilder<CourseDetailDTO>(reader)
                    .withType(CourseDetailDTO.class)
                    .withSkipLines(1)  // 첫 번째 행(헤더) 건너뛰기
                    .build()
                    .parse());
        } catch (IOException e) {
            e.printStackTrace();
            return Set.of();  // 예외 발생 시 빈 Set 반환
        }
    }

    private Set<CourseContentDto> loadCourseContentsFromFile(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            return new HashSet<>(new CsvToBeanBuilder<CourseContentDto>(reader)
                    .withType(CourseContentDto.class)
                    .withSkipLines(1)  // 첫 번째 행(헤더) 건너뛰기
                    .build()
                    .parse());
        } catch (IOException e) {
            e.printStackTrace();
            return Set.of();  // 예외 발생 시 빈 Set 반환
        }
    }


    // 전공별 과목 조회: 특정 전공의 CSV 파일 로드
    public Set<CourseDetailDTO> loadCoursesByMajor(String majorType) {
        String csvFilePath = BASE_CSV_PATH + "/"
                + majorType + "_courses_1.csv";  // 수정된 파일명
        System.out.println("전공별 CSV 파일 경로: " + csvFilePath);  // 디버깅 로깅 추가
        File csvFile = new File(csvFilePath);

        if (!csvFile.exists()) {
            System.err.println("CSV 파일을 찾을 수 없습니다: " + csvFilePath);
            return Set.of();  // 빈 리스트 반환
        }

        return loadCourseFromFile(csvFilePath);
    }

    // 전공별 과목 조회: 특정 전공의 CSV 파일 로드
    public Set<CourseContentDto> loadCoursesContentsByMajor(String majorType) {
        String csvFilePath = BASE_CSV_PATH + "/"
                + majorType + "_courses_2.csv";  // 수정된 파일명
        System.out.println("전공별 CSV 파일 경로: " + csvFilePath);  // 디버깅 로깅 추가
        File csvFile = new File(csvFilePath);

        if (!csvFile.exists()) {
            System.err.println("CSV 파일을 찾을 수 없습니다: " + csvFilePath);
            return Set.of();  // 빈 리스트 반환
        }

        return loadCourseContentsFromFile(csvFilePath);
    }


    @Transactional
    public void saveCoursesFromCSVToDatabase(String majorType) {
        // CSV 파일에서 과목 데이터를 로드
        Set<CourseDetailDTO> courseDetails = loadCoursesByMajor(majorType);

        // 각 과목 데이터를 SchoolCourse 엔티티로 변환하고 데이터베이스에 저장
        for (CourseDetailDTO courseDetail : courseDetails) {
            // 기존에 같은 ID의 SchoolCourse가 있는지 확인
            SchoolCourse existingCourse = schoolCourseRepository.findByCourseId(courseDetail.getCourseId())
                    .orElse(null);

            if (existingCourse == null) {
                // 존재하지 않으면 새로운 SchoolCourse 생성 및 저장
                SchoolCourse schoolCourse = new SchoolCourse();
                schoolCourse.setCourseId(courseDetail.getCourseId());
                schoolCourse.setCourseName(courseDetail.getCourseName());
                schoolCourse.setCourseDetails(courseDetail.getCourseDetails());
                schoolCourse.setGradeScore(courseDetail.getGradeScore());

                schoolCourseRepository.save(schoolCourse); // 데이터베이스에 저장
            } else {
                // 필요한 경우 기존 엔티티 업데이트 로직 추가
                System.out.println("Course with ID " + existingCourse.getCourseId() + " already exists. Skipping.");
            }
        }
    }

    @Transactional
    public void saveCourseContentsFromCSV(String majorType) {
        // CSV 파일에서 주차별 강의 내용을 로드
        List<CourseContentDto> courseContents = new ArrayList<>(loadCoursesContentsByMajor(majorType));

        // courseId와 week를 기준으로 정렬
        courseContents.sort(Comparator.comparing(CourseContentDto::getCourseId)
                .thenComparingInt(CourseContentDto::getWeek));

        // 각 내용을 SchoolCourseWeekContents 엔티티로 변환하고 저장
        for (CourseContentDto content : courseContents) {
            // 과목을 조회하거나 없으면 생성
            SchoolCourse schoolCourse = schoolCourseRepository.findByCourseId(content.getCourseId())
                    .orElseGet(() -> {
                        SchoolCourse newCourse = new SchoolCourse();
                        newCourse.setCourseId(content.getCourseId());
                        newCourse.setCourseName(content.getCourseName());
                        schoolCourseRepository.save(newCourse);
                        return newCourse;
                    });

            // 주차별 강의 내용이 이미 존재하는지 확인 (courseId와 week을 기준으로)
            boolean exists = schoolCourseWeekContentsRepository.existsBySchoolCourseAndWeek(schoolCourse, content.getWeek());
            if (exists) {
                // 이미 존재하는 경우 저장하지 않고 건너뜀
                continue;
            }

            // 주차별 강의 내용 저장
            SchoolCourseWeekContents weekContent = new SchoolCourseWeekContents();
            weekContent.setWeek(content.getWeek());
            weekContent.setWeeklyContent(content.getWeeklyContent());
            weekContent.setSchoolCourse(schoolCourse);

            schoolCourseWeekContentsRepository.save(weekContent);
        }
    }


    // 여기서 부터 실제 비즈니스 로직
    public List<SchoolCourse> getAllSchoolCourse(){
        return schoolCourseRepository.findAll();
    }

    public Optional<SchoolCourse> getSchoolCourseByCourseId(String courseId){
        return schoolCourseRepository.findByCourseId(courseId);
    }

    // 학과별로 과목을 조회하는 메서드 (AI, GS, IS로 구분)
    public List<SchoolCourse> getCoursesByMajor(String majorPrefix) {
        // majorPrefix는 "AI", "GS", "IS" 등으로 들어오며, 이를 기준으로 과목을 조회
        return schoolCourseRepository.findByCourseIdStartingWith(majorPrefix);
    }
    @Transactional
    public Set<SchoolCourse> saveSchoolCoursesForUser(String studentId, Set<String> courseNames) {
        // 사용자 조회
        User user = userRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + studentId));

        // 과목명을 기준으로 과목을 데이터베이스에서 조회
        Set<SchoolCourse> schoolCourses = new HashSet<>();
        for (String courseName : courseNames) {
            // 과목명을 기준으로 과목을 찾기
            SchoolCourse schoolCourse = schoolCourseRepository.findByCourseName(courseName)
                    .orElseThrow(() -> new EntityNotFoundException("Course not found with name: " + courseName));

            // 사용자의 과목 목록에 UserCourse 객체 추가
            user.addSchoolCourse(schoolCourse); 

            // 과목을 반환 리스트에 추가
            schoolCourses.add(schoolCourse);
        }

        // 사용자 정보 저장
        userRepository.save(user);

        return schoolCourses;  // 저장된 과목 리스트 반환
    }

    // 사용자가 선택한 과목에 대한 주차별 학습 내용 반환
    public Map<String, List<SchoolCourseWeekContents>> getCourseContentsForUser(String studentId) {
        // 사용자가 수강한 과목을 UserCourse를 통해 조회
        List<UserCourse> userCourses = userCourseRepository.findByUser_StudentId(studentId);

        Map<String, List<SchoolCourseWeekContents>> courseContents = new HashMap<>();

        // 각 UserCourse에 대해 주차별 학습 내용 가져오기
        for (UserCourse userCourse : userCourses) {
            SchoolCourse course = userCourse.getSchoolCourse();
            List<SchoolCourseWeekContents> weekContents = schoolCourseWeekContentsRepository.findBySchoolCourse(course);
            courseContents.put(course.getCourseName(), weekContents);  // 과목명으로 학습 내용 저장
        }

        return courseContents;
    }

    @Transactional
    public String deleteUserCourse(String studentId, String courseName) {
        // 사용자 조회
        User user = userRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + studentId));

        // 과목 조회
        SchoolCourse schoolCourse = schoolCourseRepository.findByCourseName(courseName)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with name: " + courseName));

        // 사용자가 해당 과목을 수강한 기록을 조회
        List<UserCourse> userCourses = userCourseRepository.findByUserAndSchoolCourse(user, schoolCourse);

        if (userCourses.isEmpty()) {
            throw new EntityNotFoundException("User is not enrolled in this course");
        }

        // 사용자 과목 목록에서 해당 과목 삭제
        for (UserCourse userCourse : userCourses) {
            userCourseRepository.delete(userCourse);  // 과목과 사용자 간의 관계 삭제
        }

        return "Course successfully removed from user's courses";
    }

    public List<Map<String, String>> getRecommendedCourses(List<Map<String, String>> courseInfoList) {
        List<Map<String, String>> recommendations = new ArrayList<>();

        // Flask API로 POST 요청 보내기
        RestTemplate restTemplate = new RestTemplate();
        String flaskApiUrl = "http://localhost:5000/recommend";  // Flask API 주소

        for (Map<String, String> courseInfo : courseInfoList) {
            // Flask API에 전달할 데이터 준비
            Map<String, String> requestData = new HashMap<>();
            requestData.put("courseName", courseInfo.get("courseName"));
            requestData.put("courseDetails", courseInfo.get("courseDetails"));

            // HTTP 요청 보내기
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestData, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(flaskApiUrl, requestEntity, Map.class);

            if (response.getBody() != null) {
                recommendations.add((Map<String, String>) response.getBody());
            }
        }

        return recommendations;
    }

}