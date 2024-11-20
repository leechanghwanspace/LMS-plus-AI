package com.project.LMS_plus.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class AuthController {

    @GetMapping("/session-check")
    public ResponseEntity<String> checkSession(HttpServletRequest request, Authentication authentication) {
        boolean hasSessionCookie = false;

        // Check for session cookie
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("JSESSIONID".equals(cookie.getName())) {
                    hasSessionCookie = true;
                    break;
                }
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Return response based on authentication and session cookie
        if (hasSessionCookie && authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok()
                    .headers(headers)
                    .body("{\"message\":\"Session is active\", \"authenticated\": true}");
        } else {
            return ResponseEntity.status(401)
                    .headers(headers)
                    .body("{\"message\":\"User is not logged in\", \"authenticated\": false}");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Login successful");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logout successful");
    }
}
