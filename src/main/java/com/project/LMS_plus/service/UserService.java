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
}
