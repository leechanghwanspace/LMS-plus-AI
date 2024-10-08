package com.project.LMS_plus.dto;


import com.project.LMS_plus.entity.Department;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentDto {


    private Long id;
    private String name;
    private String description;

    // JSON에서 객체를 생성할 때 사용할 생성자
    public DepartmentDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // ToEntity 함수
    public Department toEntity() {
        return new Department(name, description);
    }
}
