package com.project.LMS_plus.controller;

import com.project.LMS_plus.dto.UserDto;
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

    // 사용자 프로필 업데이트
    @PostMapping("/profile")
    public ResponseEntity<UserProfileForm> updateProfile(@RequestBody UserProfileForm form) {
        // 로그인한 사용자의 studentId를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentStudentId = authentication.getName(); // 로그인한 사용자의 studentId

        // 서비스 레이어를 통해 사용자 정보 업데이트 처리
        userService.updateUserProfile(currentStudentId, form);

        return ResponseEntity.ok(form);
    }

    @GetMapping("/checkInfo/{studentId}")
    public ResponseEntity<Boolean> checkUserInfo(@PathVariable String studentId) {
        boolean hasMajorAndGrade = userService.haveMajorAndGrade(studentId);

        return ResponseEntity.ok(hasMajorAndGrade);
    }
}
