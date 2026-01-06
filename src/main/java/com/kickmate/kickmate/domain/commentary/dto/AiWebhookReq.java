package com.kickmate.kickmate.domain.commentary.dto;

import com.kickmate.kickmate.domain.commentary.enums.Status;
import com.kickmate.kickmate.domain.commentary.enums.Tone;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AiWebhookReq {

    @NotBlank
    private Long gameId;

    @NotBlank
    private String jobId;

    @NotBlank
    private Status status;

    @Valid
    @NotNull
    private List<ScriptLine> script;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScriptLine {

        @NotBlank
        private Integer actionId;

        @NotBlank
        private String timeSeconds;

        @NotBlank
        private Tone tone;

        @NotBlank
        private String description;
    }
}
