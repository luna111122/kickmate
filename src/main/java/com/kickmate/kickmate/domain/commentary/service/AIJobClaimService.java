package com.kickmate.kickmate.domain.commentary.service;

import com.kickmate.kickmate.domain.commentary.enums.Status;
import com.kickmate.kickmate.domain.commentary.exception.CommentaryException;
import com.kickmate.kickmate.domain.commentary.exception.code.CommentaryErrorCode;
import com.kickmate.kickmate.domain.commentary.repository.AIJobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AIJobClaimService {

    private final AIJobRepository aiJobRepository;

    @Transactional
    public void markFailed(String jobId) {
        log.warn("[AI JOB CLAIM] marking job as FAILED. jobId={}", jobId);
        aiJobRepository.updateStatusByJobId(jobId, Status.FAILED);
    }

    @Transactional
    public boolean markDoneIfFirst(String jobId, Long gameId) {
        log.info("[AI JOB CLAIM] try update job status to DONE. jobId={}, gameId={}", jobId, gameId);

        // PENDING 상태인 경우에만 DONE으로 변경 (조건부 업데이트)
        int updated = aiJobRepository.updateStatusByJobIdAndStatus(jobId, Status.PENDING, Status.DONE);

        if (updated == 1) {
            log.info("[AI JOB CLAIM] job claimed successfully. jobId={}, gameId={}", jobId, gameId);
            return true;
        }

        // updated == 0: jobId가 없거나 이미 처리된 경우 → 두 케이스 구분
        boolean exists = aiJobRepository.findByJobId(jobId).isPresent();
        if (!exists) {
            log.error("[AI JOB CLAIM] job not found. jobId={}, gameId={}", jobId, gameId);
            throw new CommentaryException(CommentaryErrorCode.AI_JOB_NOT_FOUND);
        }

        // job은 존재하지만 PENDING이 아님 → 이미 처리된 중복 웹훅
        log.warn("[AI JOB CLAIM] duplicate webhook ignored. already processed. jobId={}, gameId={}", jobId, gameId);
        return false;
    }
}
