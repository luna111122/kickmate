package com.kickmate.kickmate.domain.commentary.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(
        name = "ai_job",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_ai_job_job_id", columnNames = "job_id")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AiJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_id", nullable = false, length = 64)
    private String jobId;

    @Column(name = "game_id", nullable = false, length = 32)
    private String gameId;

    @Column(nullable = false, length = 10)
    private String status; // PENDING / DONE

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}