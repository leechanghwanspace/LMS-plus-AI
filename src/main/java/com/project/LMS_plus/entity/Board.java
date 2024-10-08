package com.project.LMS_plus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Board {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private User user;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

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
