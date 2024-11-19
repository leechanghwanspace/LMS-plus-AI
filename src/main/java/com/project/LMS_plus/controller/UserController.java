package com.project.LMS_plus.controller;

import com.project.LMS_plus.dto.SignUpForm;
import com.project.LMS_plus.dto.UserDto;
import com.project.LMS_plus.entity.SchoolCourse;
import com.project.LMS_plus.entity.SchoolCourseWeekContents;
import com.project.LMS_plus.entity.User;
import com.project.LMS_plus.entity.UserCourse;
import com.project.LMS_plus.service.SchoolCourseService;
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

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private SchoolCourseService schoolCourseService;


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

    @Operation(summary = "마이페이지 정보 조회(수강과목 포함)", description = "특정 사용자 ID를 사용해 마이페이지 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    @GetMapping("/mypage/schoolCourse/{studentId}")
    public ResponseEntity<UserDto> loadUserSchoolCourse(@PathVariable String studentId) {
        UserDto userDto = userService.loadUserSchoolCourseInfo(studentId);
        return ResponseEntity.ok(userDto);
    }

    /**
     * 마이페이지 정보 불러오기
     * 특정 사용자 ID로 마이페이지 정보를 불러옵니다.
     *
     * @param studentId 사용자 ID
     * @return 사용자 정보 DTO
     */
    @Operation(summary = "마이페이지 정보 조회(수강과목 미포함)", description = "특정 사용자 ID를 사용해 마이페이지 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    @GetMapping("/mypage/{studentId}")
    public ResponseEntity<UserDto> loadUserInfo(@PathVariable String studentId) {
        UserDto userDto = userService.loadUserInfo(studentId);
        return ResponseEntity.ok(userDto);
    }

    @Operation(summary = "사용자에 수강과목 데이터가 있으면 불러오기", description = "특정 사용자 ID를 사용해 수강중인 교과목을 불러옵니다.")
    @GetMapping("/have/schoolCourse/{studentId}")
    public ResponseEntity<List<UserCourse>> userHaveSchoolCourse(@PathVariable String studentId){
        if(userService.haveSchoolSubject(studentId)){
            List<UserCourse> userSchoolCourse = userService.loadOnlyUserSchoolCourse(studentId);

            return ResponseEntity.ok(userSchoolCourse);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @Operation(summary = "학생 ID로 과목 저장")
    @PostMapping("/{studentId}")
    public ResponseEntity<Set<SchoolCourse>> saveSchoolCourse(@PathVariable String studentId, @RequestBody Set<String> courseNames) {
        // 과목 저장 서비스 호출
        Set<SchoolCourse> savedCourses = schoolCourseService.saveSchoolCoursesForUser(studentId, courseNames);
        // 저장된 과목들 반환
        return ResponseEntity.ok(savedCourses);
    }

    @GetMapping("/{studentId}/contents")
    public ResponseEntity<Map<String, List<SchoolCourseWeekContents>>> getCourseContentsForUser(
            @PathVariable String studentId) {
        Map<String, List<SchoolCourseWeekContents>> courseContents = schoolCourseService.getCourseContentsForUser(studentId);
        return ResponseEntity.ok(courseContents);
    }
    @DeleteMapping("/{studentId}/deleteCourse")
    public ResponseEntity<String> deleteUserCourse(@PathVariable String studentId, @RequestParam String courseName) {
        try {
            String responseMessage = schoolCourseService.deleteUserCourse(studentId, courseName);
            return ResponseEntity.ok(responseMessage);  // 성공적으로 삭제된 경우 200 OK 응답 반환
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());  // 에러가 발생한 경우 404 Not Found 응답
        }
    }
}
