package com.project.LMS_plus.entity;

import com.project.LMS_plus.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Entity
@Getter
@Setter
public class Department {
    @Id
    private Long departmentId;

    @Column
    private String name;

    @Column
    private String description;


    public Department(Long departmentId, String name, String description) {
        this.departmentId = departmentId;
        this.name = name;
        this.description = description;
    }

    public Department() {

    }
}