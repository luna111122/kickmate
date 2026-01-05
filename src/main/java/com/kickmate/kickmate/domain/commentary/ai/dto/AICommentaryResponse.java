package com.kickmate.kickmate.domain.commentary.ai.dto;

import com.kickmate.kickmate.domain.commentary.ai.enums.AIJobStatus;
import lombok.Getter;

@Getter
public class AICommentaryResponse {
    private String jobId;
    private AIJobStatus status;
}