package com.project.LMS_plus.service;

import com.project.LMS_plus.dto.SignUpForm;
import com.project.LMS_plus.dto.UserDto;
import com.project.LMS_plus.dto.UserProfileForm;
import com.project.LMS_plus.entity.Department;
import com.project.LMS_plus.entity.Job;
import com.project.LMS_plus.entity.User;
import com.project.LMS_plus.repository.DepartmentRepository;
import com.project.LMS_plus.repository.JobRepository;
import com.project.LMS_plus.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.channels.IllegalChannelGroupException;

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
    @Autowired
    private JobService jobService;


    // 기존 회원가입 로직
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

        // 복수 전공이 있을 경우 '기타'로 설정
        String doubleMajor = form.getDoubleMajor();
        if (doubleMajor != null && !doubleMajor.equals("없음")) {
            doubleMajor = "기타";
        }

        // 사용자 정보 업데이트
        user.setDepartment(department);
        user.setMajor(form.getMajor());
        user.setDoubleMajor(doubleMajor);
        user.setYear(form.getYear());
        user.setJob(job);

        // 변경된 사용자 정보 저장
        userRepository.save(user);
    }

    @Transactional
    public String modifyName(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        // 사용자의 이름 가져오기
        String originalName = user.getName();

        // 이름이 null이 아니고 길이가 1 이상인 경우만 처리
        if (originalName != null && originalName.length() > 1) {
            // 첫 글자를 제외한 나머지 글자를 "xx" 또는 "oo"로 바꾸기
            String modifiedName = originalName.charAt(0) + "X".repeat(originalName.length() - 1);
            return modifiedName;
        } else if (originalName != null && originalName.length() == 1) {
            // 이름이 한 글자일 경우 그대로 반환
            return originalName;
        } else {
            throw new IllegalArgumentException("Name is invalid or empty for user with id: " + userId);
        }
    }

    @Transactional
    public UserDto loadUserInfo(String userId) {
        User user = userRepository.findByStudentId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        return new UserDto(
                user.getStudentId(),
                user.getEmail(),
                user.getName(),
                user.getMajor(),
                user.getDoubleMajor(),
                user.getYear(),
                user.getDepartment().getName(),
                user.getJob()
        );
    }

    @Transactional
    public UserDto loadUserSchoolCourseInfo(String userId) {
        User user = userRepository.findByStudentId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        return new UserDto(
                user.getStudentId(),
                user.getEmail(),
                user.getName(),
                user.getMajor(),
                user.getDoubleMajor(),
                user.getYear(),
                user.getDepartment().getName(),
                user.getJob(),
                user.getSchoolCourses()
        );
    }

    @Transactional
    public boolean haveMajorAndGrade(String userId) {
        User user = userRepository.findByStudentId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with student ID: " + userId));

        // `major`, `doubleMajor`, `department`, 'year' 가 모두 존재하는지 여부 확인
        return user.getMajor() != null && user.getDoubleMajor() != null && user.getDepartment() != null && user.getYear() != null && user.getJob() != null;
    }
}