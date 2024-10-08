package com.project.LMS_plus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    // 생성자
    public Department() {
    }

    public Department(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
