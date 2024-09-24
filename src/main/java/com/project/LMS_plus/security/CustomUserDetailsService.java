package com.project.LMS_plus.security;

import com.project.LMS_plus.entity.User;
import com.project.LMS_plus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String studentId) throws UsernameNotFoundException {
        User user = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + studentId));

        // 비밀번호 검증 로직은 Spring Security 내부에서 처리됩니다.
        return new CustomUserDetails(user);
    }
}
