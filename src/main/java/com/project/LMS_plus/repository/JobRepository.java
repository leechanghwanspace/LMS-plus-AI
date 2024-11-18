package com.project.LMS_plus.repository;

import com.project.LMS_plus.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {
    Optional<Job> findByJobName(String jobName);

    Long findIdByJobName(String jobName);

    boolean existsByJobName(String name);
}
