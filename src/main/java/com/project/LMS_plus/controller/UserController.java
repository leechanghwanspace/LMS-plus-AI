package com.project.LMS_plus.controller;

import com.project.LMS_plus.dto.SignUpForm;
import com.project.LMS_plus.dto.UserDto;
import com.project.LMS_plus.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 회원가입 엔드포인트
     * 사용자가 회원가입 양식을 제출하면 이를 처리하여 새로운 사용자를 등록합니다.
     *
     * @param form 회원가입 양식 데이터
     * @param bindingResult 유효성 검사 결과
     * @return 성공 메시지 또는 오류 메시지
     */
    @Operation(summary = "회원가입", description = "회원가입 양식을 제출하여 새로운 사용자를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "회원가입 실패 (유효성 검사 실패 또는 비밀번호 불일치)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignUpForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }

        try {
            userService.registerUser(form);
            return ResponseEntity.ok("회원가입 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 마이페이지 정보 불러오기
     * 특정 사용자 ID로 마이페이지 정보를 불러옵니다.
     *
     * @param userId 사용자 ID
     * @return 사용자 정보 DTO
     */
    @Operation(summary = "마이페이지 정보 조회", description = "특정 사용자 ID를 사용해 마이페이지 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    @GetMapping("/mypage/{userId}")
    public ResponseEntity<UserDto> loadUserInfo(@PathVariable String userId) {
        UserDto userDto = userService.loadUserInfo(userId);
        return ResponseEntity.ok(userDto);
    }

    /**
     * 사용자 직무 업데이트
     * 사용자 정보를 기반으로 새로운 직무 정보를 업데이트합니다.
     *
     * @param userDto 사용자 정보와 직무 정보를 포함한 DTO
     * @return 업데이트된 사용자 정보
     */
    @Operation(summary = "사용자 직무 업데이트", description = "사용자 ID와 새로운 직무를 받아서 사용자의 직무 정보를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 직무 업데이트 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 사용자 정보 또는 직무 정보")
    })
    @PostMapping("/update/userjob")
    public ResponseEntity<UserDto> updateUserJob(@RequestBody UserDto userDto) {
        userService.savingUserJob(userDto.getStudentId(), String.valueOf(userDto.getJob()));
        return ResponseEntity.ok(userDto);
    }
}
