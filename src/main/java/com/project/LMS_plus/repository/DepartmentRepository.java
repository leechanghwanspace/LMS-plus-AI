package com.project.LMS_plus.repository;

import com.project.LMS_plus.entity.Department;
import com.project.LMS_plus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByDepartmentId(Long departmentId);
}
