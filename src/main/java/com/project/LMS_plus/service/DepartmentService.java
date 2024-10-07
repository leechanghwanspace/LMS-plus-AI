package com.project.LMS_plus.service;

import com.project.LMS_plus.dto.DepartmentDto;
import com.project.LMS_plus.entity.Department;
import com.project.LMS_plus.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    // Create (저장)
    public Department createDepartment(DepartmentDto departmentDto) {
        Department department = departmentDto.toEntity();
        return departmentRepository.save(department);
    }

    // Read (모든 부서 가져오기)
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // Read (ID로 특정 부서 가져오기)
    public Optional<Department> getDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId);
    }

    // Update (부서 정보 수정)
    public Department updateDepartment(Long departmentId, DepartmentDto departmentDto) {
        // 먼저 기존 부서를 조회
        Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);

        if (optionalDepartment.isPresent()) {
            Department department = optionalDepartment.get();
            department.setName(departmentDto.getName());
            department.setDescription(departmentDto.getDescription());
            return departmentRepository.save(department); // 수정된 부서를 저장
        } else {
            throw new IllegalArgumentException("해당 ID의 부서가 존재하지 않습니다.");
        }
    }

    // Delete (부서 삭제)
    public void deleteDepartment(Long departmentId) {
        departmentRepository.deleteById(departmentId);
    }
}