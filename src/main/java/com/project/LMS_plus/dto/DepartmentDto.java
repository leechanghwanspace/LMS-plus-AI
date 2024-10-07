package com.project.LMS_plus.dto;

import com.project.LMS_plus.entity.Department;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentDto {

    private Long departmentId;
    private String name;
    private String description;


    // ToEntity 함수
    public Department toEntity() {
        return new Department(departmentId, name, description);
    }
}
