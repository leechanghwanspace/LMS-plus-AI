package com.project.LMS_plus.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private User user;

    @Lob  // @Lob 어노테이션을 사용하여 'TEXT' 타입 설정
    @Column(nullable = false)  // not null로 설정
    private String title;

    @Lob  // @Lob 어노테이션을 사용하여 'TEXT' 타입 설정
    @Column(nullable = false)  // not null로 설정
    private String content;  // 게시글 내용

    @Column
    private String writer;

    @Column
    private LocalDateTime postedTime;

    @Column
    private LocalDateTime updatedTime;


    @PrePersist
    protected void onCreate() {
        postedTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedTime = LocalDateTime.now();
    }

}
