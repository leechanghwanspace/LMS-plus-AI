package com.project.LMS_plus.service;

import com.opencsv.bean.CsvToBeanBuilder;
import com.project.LMS_plus.entity.Job;
import com.project.LMS_plus.entity.RecommendCourse;
import com.project.LMS_plus.entity.User;
import com.project.LMS_plus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendService {

    private final UserRepository userRepository;

    // CSV 파일을 읽어 RecommendCourse 목록으로 변환하는 메서드
    public List<RecommendCourse> readCsv(String filePath) {
        try {
            return new CsvToBeanBuilder<RecommendCourse>(new FileReader(filePath))
                    .withType(RecommendCourse.class)
                    .build()
                    .parse();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 특정 직무와 일치하는 강의 목록 필터링
    public List<RecommendCourse> selectingSameJob(List<RecommendCourse> courses, Job job) {
        return courses.stream()
                .filter(course -> course.getJob() != null && course.getJob().getId().equals(job.getId()))
                .collect(Collectors.toList());
    }

    // 특정 직무와 가장 높은 일치율을 가진 강의 추천
    public Optional<RecommendCourse> recommendHigherSameRate(List<RecommendCourse> courses, Job job) {
        return selectingSameJob(courses, job).stream()
                .max(Comparator.comparingDouble(this::parseMatchRate));
    }

    // RecommendCourse에서 일치율을 파싱하여 반환하는 메서드
    private double parseMatchRate(RecommendCourse course) {
        String matchRateStr = String.valueOf(course.getMatchRate());
        try {
            return matchRateStr != null ? Double.parseDouble(matchRateStr) : 0.0;
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    // 사용자 직무 기반 강의 추천 메서드
    public List<RecommendCourse> getCoursesByUserJob(String studentId, List<RecommendCourse> courses) {
        User user = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + studentId));

        Job userJob = user.getJob();
        return courses.stream()
                .filter(course -> course.getJob() != null && course.getJob().getId().equals(userJob.getId()))
                .collect(Collectors.toList());
    }

    // 가장 높은 일치율을 가진 강의 추천 메서드
    public Optional<RecommendCourse> getTopMatchCourseByUserJob(String studentId, List<RecommendCourse> courses) {
        return getCoursesByUserJob(studentId, courses).stream()
                .max(Comparator.comparingDouble(RecommendCourse::getMatchRate));
    }

//    public String3. 전공에 맞는 과목 추천
//전공과 직무에 맞는 과목을 추천할 수 있습니다. 예를 들어, majorNames와 일치하는 직무가 있는 과목을 찾아 추천할 수 있습니다.
//
//java
//코드 복사
//public List<Course> recommendCoursesByMajor(List<String> majorNames) {
//    List<Course> recommendedCourses = new ArrayList<>();
//    for (Course course : allCourses) {
//        for (String majorName : majorNames) {
//            // 예: majorName이 직무명과 일치하는 과목 찾기
//            if (course.getJobTitles().contains(majorName)) {
//                recommendedCourses.add(course);
//            }
//        }
//    }
//    // 일치율로 정렬
//    recommendedCourses.sort(Comparator.comparingDouble(Course::getMatchRate).reversed());
//    return recommendedCourses;
//}
}
