package com.kickmate.kickmate.domain.commentary.repository;

import com.kickmate.kickmate.domain.commentary.entity.AiJob;
import com.kickmate.kickmate.domain.commentary.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AIJobRepository extends JpaRepository<AiJob, Long> {
    Optional<AiJob> findByJobId(String jobId);

    @Modifying
    @Query("""
    UPDATE AiJob a
    SET a.status = :status
    WHERE a.jobId = :jobId
""")
    int updateStatusByJobId(
            @Param("jobId") String jobId,
            @Param("status") Status status
    );

    @Modifying
    @Query("""
    UPDATE AiJob a
    SET a.status = :newStatus
    WHERE a.jobId = :jobId
    AND a.status = :expectedStatus
""")
    int updateStatusByJobIdAndStatus(
            @Param("jobId") String jobId,
            @Param("expectedStatus") Status expectedStatus,
            @Param("newStatus") Status newStatus
    );
}
