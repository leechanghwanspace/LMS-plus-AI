package com.project.LMS_plus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User {

    @Id
    private String studentId;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String major; // 주 전공

    @Column
    private String doubleMajor; // 복수 전공

    @Column
    private Integer year; // 학년


    @ManyToOne
    @JoinColumn(name = "job_id")  // 외래 키로 `Job` 엔티티를 참조
    private Job job;          // 사용자가 희망하는 직업군

    @ManyToOne
    @JoinColumn(name = "department_id")  // User 테이블에 외래 키로 추가됨
    private Department department;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Board> boards = new ArrayList<>();

    // 교과목
    // 기본 생성자
    public User() {}

    // 생성자
    public User(String studentId, String email, String password, String name, String major, String doubleMajor, Integer year, Department department) {
        this.studentId = studentId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.major = major;
        this.doubleMajor = doubleMajor;
        this.year = year;
        this.department = department;
    }

    public void addBoard(Board board) {
        this.boards.add(board);
        board.setUser(this); // 보드의 사용자 정보를 설정합니다.
    }
}
