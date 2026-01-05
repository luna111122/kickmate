package com.kickmate.kickmate.domain.commentary.dto;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
public class StartCommentaryRes {

    @Nonnull
    String jobId;

    @Nonnull
    String fillerAudioUrl;

}
