package com.project.LMS_plus.repository;

import com.project.LMS_plus.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
