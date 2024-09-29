package com.project.LMS_plus.controller;

import com.project.LMS_plus.dto.UserProfileForm;
import com.project.LMS_plus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserProfileController {

    @Autowired
    private UserService userService;

    @PostMapping("/profile")
    public ResponseEntity<String> updateProfile(@RequestBody UserProfileForm form) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentStudentId = authentication.getName(); // 로그인한 사용자의 studentId

        // 서비스 레이어를 통해 사용자 정보 업데이트 처리
        userService.updateUserProfile(currentStudentId, form.getDepartment(), form.getMajor(), form.getDoubleMajor(), form.getYear());

        return ResponseEntity.ok("사용자 정보가 업데이트되었습니다.");
    }
}
