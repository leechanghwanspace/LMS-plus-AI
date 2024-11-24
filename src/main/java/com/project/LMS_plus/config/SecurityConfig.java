package com.project.LMS_plus.config;

import com.project.LMS_plus.entity.User;
import com.project.LMS_plus.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository; // UserService 대신 UserRepository 사용
    @Value("${frontend.url}")
    private String frontendUrl;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.addAllowedOrigin("https://red-pen-lms-fe.vercel.app");
                    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "ngrok-skip-browser-warning"));
                    return config;
                }))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login", "/api/logout", "/api/signup","/api/session-check").permitAll()
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/courses/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/api/login")
                        .usernameParameter("studentId")
                        .passwordParameter("password")
                        .successHandler(successHandler())
                        .failureHandler(failureHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setContentType("application/json");
                            response.setCharacterEncoding("utf-8");
                            // 로그아웃 후 JSESSIONID 쿠키 삭제
                            Cookie cookie = new Cookie("JSESSIONID", null);
                            cookie.setHttpOnly(true);
                            cookie.setSecure(true);
                            cookie.setPath("/");
                            cookie.setMaxAge(0);  // 쿠키 만료
                            response.addCookie(cookie);
                            response.getWriter().write("{\"message\":\"로그아웃 성공\"}");
                        })
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType("application/json");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("{\"message\":\"인증이 필요합니다.\"}");
                        })
                )
                .sessionManagement(session -> session
                        .maximumSessions(1)
                );


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");

            // 세션 생성
            String sessionId = request.getSession().getId();  // 세션 ID 가져오기

            // JSESSIONID 쿠키 설정
            Cookie cookie = new Cookie("JSESSIONID", sessionId);
            cookie.setHttpOnly(true);  // 보안을 위해 HttpOnly 속성 추가
            cookie.setSecure(true);    // HTTPS 환경에서만 쿠키 전송
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60); // 쿠키 유효 시간 설정 (1시간)
            response.addCookie(cookie);  // 쿠키를 응답에 추가

            // studentId 추출
            String studentId = authentication.getName();

            // 사용자 정보 조회
            User user = userRepository.findByStudentId(studentId)
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

            // jobId가 null일 경우 null 반환, 존재하면 jobId 반환
            String jobId = String.valueOf((user.getJob() != null) ? user.getJob().getId() : null);

            // JSON 응답 생성
            String jsonResponse = String.format(
                    "{\"message\":\"로그인 성공\", \"studentId\":\"%s\", \"userName\":\"%s\", \"jobId\":%s}",
                    studentId, user.getName(), (jobId == null ? "null" : "\"" + jobId + "\"")
            );

            response.setHeader("Set-Cookie", "JSESSIONID=" + sessionId + "; Secure; HttpOnly; SameSite=None; Path=/; Max-Age=3600");
            // JSON 응답 전송
            response.getWriter().write(jsonResponse);
        };
    }


    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return (request, response, exception) -> {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"message\":\"로그인 실패\"}");
        };
    }
}
