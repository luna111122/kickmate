package com.kickmate.kickmate.domain.commentary.service;


import com.kickmate.kickmate.domain.commentary.entity.AiJob;
import com.kickmate.kickmate.domain.commentary.enums.Status;
import com.kickmate.kickmate.domain.commentary.exception.CommentaryException;
import com.kickmate.kickmate.domain.commentary.exception.code.CommentaryErrorCode;
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
    public boolean markDoneIfFirst(String jobId, Long gameId) {
        try {
            int updated = aiJobRepository.updateStatusByJobId(
                    jobId,
                    Status.DONE
            );

            if (updated == 0) {
                throw new CommentaryException(CommentaryErrorCode.AI_JOB_NOT_FOUND);
            }
            return true;

        } catch (DataIntegrityViolationException e) {
            // 이미 처리된 jobId (중복 webhook)
            return false;
        }
    }
}
