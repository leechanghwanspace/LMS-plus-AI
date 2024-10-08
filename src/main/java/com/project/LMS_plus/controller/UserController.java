package com.project.LMS_plus.controller;

import com.project.LMS_plus.dto.SignUpForm;
import com.project.LMS_plus.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class UserController {

    @Autowired  // UserService 의존성 주입
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignUpForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시 에러 메시지 반환
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }

        try {
            // 회원가입 처리
            userService.registerUser(form);
            return ResponseEntity.ok("회원가입 성공");
        } catch (IllegalArgumentException e) {
            // 비밀번호 불일치 시 오류 메시지 반환
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}