package com.project.LMS_plus.service;

import com.project.LMS_plus.dto.SignUpForm;
import com.project.LMS_plus.entity.User;
import com.project.LMS_plus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 기존 회원가입 로직
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

    public void updateUserProfile(String studentId, String department, String major, String doubleMajor, Integer year) {
        User user = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 소프트웨어공학부의 경우 전공 선택이 필요
        if (department.equals("소프트웨어공학부")) {
            if (major == null || (!major.equals("게임소프트웨어전공") && !major.equals("인공지능전공") && !major.equals("정보보호학전공"))) {
                throw new IllegalArgumentException("소프트웨어공학부에서는 게임소프트웨어전공, 인공지능전공, 정보보호학전공 중 하나를 선택해야 합니다.");
            }
        }

        // 복수 전공일 시 doubleMajor 값을 "기타"로 고정
        if (doubleMajor != null && !doubleMajor.equals("없음")) {
            doubleMajor = "기타";
        }

        user.setMajor(major);
        user.setDoubleMajor(doubleMajor);
        user.setYear(year);

        userRepository.save(user);
    }

}
