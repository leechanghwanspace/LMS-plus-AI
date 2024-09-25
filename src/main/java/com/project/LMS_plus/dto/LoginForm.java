package com.project.LMS_plus.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {

    private String studentId;  // 학번 (로그인용 ID)
    private String password;   // 비밀번호

    // 기본 생성자
    public LoginForm() {
    }

    // 매개변수 있는 생성자
    public LoginForm(String studentId, String password) {
        this.studentId = studentId;
        this.password = password;
    }
}
