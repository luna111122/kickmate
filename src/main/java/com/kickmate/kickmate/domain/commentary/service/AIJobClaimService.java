package com.kickmate.kickmate.domain.commentary.service;

import com.kickmate.kickmate.domain.commentary.entity.AiJob;
import com.kickmate.kickmate.domain.commentary.enums.Status;
import com.kickmate.kickmate.domain.commentary.exception.CommentaryException;
import com.kickmate.kickmate.domain.commentary.exception.code.CommentaryErrorCode;
import com.kickmate.kickmate.domain.commentary.repository.AIJobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AIJobClaimService {

    private final AIJobRepository aiJobRepository;

    @Transactional
    public boolean markDoneIfFirst(String jobId, Long gameId) {
        try {
            // [JOB-STATUS-UPDATE] jobId 상태를 DONE으로 변경 시도 (멱등 처리 핵심)
            log.info("[AI JOB CLAIM] try update job status to DONE. jobId={}, gameId={}",
                    jobId, gameId);

            int updated = aiJobRepository.updateStatusByJobId(
                    jobId,
                    Status.DONE
            );

            if (updated == 0) {
                // [JOB-NOT-FOUND] 존재하지 않는 jobId
                log.error("[AI JOB CLAIM] job not found. jobId={}, gameId={}",
                        jobId, gameId);
                throw new CommentaryException(CommentaryErrorCode.AI_JOB_NOT_FOUND);
            }

            // [JOB-CLAIM-SUCCESS] 최초로 DONE 처리 성공
            log.info("[AI JOB CLAIM] job claimed successfully. jobId={}, gameId={}",
                    jobId, gameId);

            return true;

        } catch (DataIntegrityViolationException e) {
            // [DUPLICATE-JOB] 이미 DONE 처리된 jobId → 중복 웹훅
            log.warn("[AI JOB CLAIM] duplicate job detected. already processed. jobId={}, gameId={}",
                    jobId, gameId);
            return false;
        }
    }
}
