package com.kickmate.kickmate.domain.commentary.ai.dto;


import com.kickmate.kickmate.domain.commentary.enums.Status;
import lombok.Getter;

@Getter
public class AICommentaryResponse {
    private String jobId;
    private Status status;
}