package com.project.LMS_plus.repository;

import com.project.LMS_plus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByStudentId(String studentId);
}
