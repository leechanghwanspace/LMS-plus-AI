package com.project.LMS_plus.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignUpForm {

    @NotBlank(message = "학번은 필수 입력 항목입니다.")
    @Size(min = 8, max = 8, message = "학번은 8자리 숫자여야 합니다.")
    private String studentId;

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "유효하지 않은 이메일 형식입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자리 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 입력 항목입니다.")
    private String confirmPassword;

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;

    public @NotBlank(message = "학번은 필수 입력 항목입니다.") @Size(min = 8, max = 8, message = "학번은 8자리 숫자여야 합니다.") String getStudentId() {
        return studentId;
    }

    public void setStudentId(@NotBlank(message = "학번은 필수 입력 항목입니다.") @Size(min = 8, max = 8, message = "학번은 8자리 숫자여야 합니다.") String studentId) {
        this.studentId = studentId;
    }

    public @NotBlank(message = "이메일은 필수 입력 항목입니다.") @Email(message = "유효하지 않은 이메일 형식입니다.") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "이메일은 필수 입력 항목입니다.") @Email(message = "유효하지 않은 이메일 형식입니다.") String email) {
        this.email = email;
    }

    public @NotBlank(message = "비밀번호는 필수 입력 항목입니다.") @Size(min = 8, message = "비밀번호는 최소 8자리 이상이어야 합니다.") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "비밀번호는 필수 입력 항목입니다.") @Size(min = 8, message = "비밀번호는 최소 8자리 이상이어야 합니다.") String password) {
        this.password = password;
    }

    public @NotBlank(message = "비밀번호 확인은 필수 입력 항목입니다.") String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(@NotBlank(message = "비밀번호 확인은 필수 입력 항목입니다.") String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public @NotBlank(message = "이름은 필수 입력 항목입니다.") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "이름은 필수 입력 항목입니다.") String name) {
        this.name = name;
    }
}