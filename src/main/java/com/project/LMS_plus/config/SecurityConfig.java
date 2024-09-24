package com.project.LMS_plus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 보호 비활성화 (API 호출 시)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login", "/api/signup").permitAll()  // 로그인, 회원가입은 인증 없이 접근 가능
                        .anyRequest().authenticated()  // 나머지는 인증 필요
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/api/login")  // 로그인 처리 URL
                        .usernameParameter("studentId")    // 학번을 사용자 ID로 사용
                        .passwordParameter("password")     // 패스워드 필드
                        .defaultSuccessUrl("/dashboard")   // 로그인 성공 시 리다이렉트 URL
                        .failureUrl("/login?error=true")   // 실패 시 리다이렉트 URL
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .maximumSessions(1)  // 세션 하나만 허용
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // 비밀번호 암호화
    }
}
