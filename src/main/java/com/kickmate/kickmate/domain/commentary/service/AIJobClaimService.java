package com.kickmate.kickmate.domain.commentary.service;


import com.kickmate.kickmate.domain.commentary.entity.AiJob;
import com.kickmate.kickmate.domain.commentary.repository.AIJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AIJobClaimService {

    private final AIJobRepository aiJobRepository;

    @Transactional
    public boolean markDoneIfFirst(String jobId, String gameId) {
        try {
            AiJob job = AiJob.builder()
                    .jobId(jobId)
                    .gameId(gameId)
                    .status("DONE")
                    .createdAt(LocalDateTime.now())
                    .build();

            aiJobRepository.save(job);
            return true;

        } catch (DataIntegrityViolationException e) {
            // 이미 처리된 jobId (중복 webhook)
            return false;
        }
    }
}
