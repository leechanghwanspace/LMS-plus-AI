package com.project.LMS_plus.service;

import com.project.LMS_plus.dto.SignUpForm;
import com.project.LMS_plus.dto.UserDto;
import com.project.LMS_plus.dto.UserProfileForm;
import com.project.LMS_plus.entity.*;
import com.project.LMS_plus.repository.DepartmentRepository;
import com.project.LMS_plus.repository.JobRepository;
import com.project.LMS_plus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JobRepository jobRepository;


    // 회원가입 로직
    @Transactional
    public void registerUser(SignUpForm form) {

        // 비밀번호와 비밀번호 확인 값이 일치하는지 확인
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인 값이 일치하지 않습니다.");
        }

        // 사용자 정보 저장 로직
        User user = new User();
        user.setStudentId(form.getStudentId());
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword())); // 비밀번호 암호화
        user.setName(form.getName());
        userRepository.save(user);  // DB에 저장
    }

    @Transactional
    public void updateUserProfile(String studentId, UserProfileForm form) {
        // 사용자 조회
        User user = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 학부 조회
        Department department = departmentRepository.findById(form.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("학부가 존재하지 않습니다."));

        Job job = jobRepository.findById(form.getJobId())
                .orElseThrow(() -> new IllegalArgumentException("직업이 존재하지 않습니다."));

        // 소프트웨어공학부의 경우 전공 선택 검증
        if (department.getId() == 1) { // departmentId가 1인 경우를 소프트웨어공학부로 가정
            if (form.getMajor() == null || (!form.getMajor().equals("게임소프트웨어전공")
                    && !form.getMajor().equals("인공지능전공")
                    && !form.getMajor().equals("정보보호학전공"))) {
                throw new IllegalArgumentException("소프트웨어공학부에서는 게임소프트웨어전공, 인공지능전공, 정보보호학전공 중 하나를 선택해야 합니다.");
            }
        }

        // 사용자 정보 업데이트
        user.setDepartment(department);
        user.setMajor(form.getMajor());
        user.setYear(form.getYear());
        user.setJob(job);

        // 변경된 사용자 정보 저장
        userRepository.save(user);
    }

    @Transactional
    public String modifyName(String studentId) {
        User user = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + studentId));

        String originalName = user.getName();

        if (originalName != null && originalName.length() > 1) {
            String modifiedName = originalName.charAt(0) + "X".repeat(originalName.length() - 1);
            return modifiedName;
        } else if (originalName != null && originalName.length() == 1) {
            return originalName;
        } else {
            throw new IllegalArgumentException("Name is invalid or empty for user with id: " + studentId);
        }
    }

    @Transactional
    public UserDto loadUserInfo(String studentId) {
        User user = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + studentId));

        // UserDto 생성 시, userCourses 정보를 포함하도록 수정
        return new UserDto(
                user.getStudentId(),
                user.getEmail(),
                user.getName(),
                user.getMajor(),
                user.getYear(),
                user.getDepartment().getName(),
                user.getJob(),
                user.getUserCourses() // UserCourse 리스트로 수정
        );
    }


    @Transactional
    public UserDto loadUserSchoolCourseInfo(String studentId) {
        User user = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + studentId));

        // UserDto 생성 시, userCourses 정보를 포함하도록 수정
        return new UserDto(
                user.getStudentId(),
                user.getEmail(),
                user.getName(),
                user.getMajor(),
                user.getYear(),
                user.getDepartment().getName(),
                user.getJob(),
                user.getUserCourses() // UserCourse 리스트로 수정
        );
    }


    @Transactional
    public List<UserCourse> loadOnlyUserSchoolCourse(String studentId) {
        User user = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + studentId));

        return user.getUserCourses();
    }

    @Transactional
    public boolean haveMajorAndGrade(String studentId) {
        User user = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with student ID: " + studentId));

        return user.getMajor() != null && user.getDepartment() != null && user.getYear() != null && user.getJob() != null;
    }

    @Transactional
    public boolean haveSchoolSubject(String studentId) {
        User user = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with student ID: " + studentId));

        return user.getUserCourses() != null;
    }
    @Transactional
    public List<Map<String, String>> loadUserSchoolCourseNameAndDetails(String studentId) {
        // 사용자 정보 조회
        User user = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + studentId));

        // UserCourse 리스트에서 courseName과 courseDetails만 추출하여 반환
        return user.getUserCourses().stream()
                .map(userCourse -> {
                    SchoolCourse schoolCourse = userCourse.getSchoolCourse();
                    Map<String, String> courseInfo = new HashMap<>();
                    courseInfo.put("courseName", schoolCourse.getCourseName());
                    courseInfo.put("courseDetails", schoolCourse.getCourseDetails());
                    return courseInfo;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public Map<String, String> loadUserSchoolCourseNameAndDetailsByCourseId(String studentId, String courseId) {
        // 사용자 정보 조회
        User user = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + studentId));

        // 해당 사용자가 수강한 강의 중 courseId가 일치하는 강의 찾기
        UserCourse userCourse = user.getUserCourses().stream()
                .filter(uc -> uc.getSchoolCourse().getCourseId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + courseId));

        // 해당 강의의 courseName과 courseDetails를 Map에 담아서 반환
        SchoolCourse schoolCourse = userCourse.getSchoolCourse();
        Map<String, String> courseInfo = new HashMap<>();
        courseInfo.put("courseName", schoolCourse.getCourseName());
        courseInfo.put("courseDetails", schoolCourse.getCourseDetails());

        return courseInfo;
    }
}
