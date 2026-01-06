package com.kickmate.kickmate.domain.commentary.repository;

import com.kickmate.kickmate.domain.commentary.entity.AiJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AIJobRepository extends JpaRepository<AiJob, Long> {
    Optional<AiJob> findByJobId(String jobId);
}
