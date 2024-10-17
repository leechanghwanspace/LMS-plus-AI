package com.project.LMS_plus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comment {

    @Id
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    @Column
    @CreatedDate
    private LocalDateTime createdDate;

    @Column
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Column
    private String writer;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private User user;

    //댓글 수정
    public void update(String comment) {
        this.comment = comment;
    }
}
