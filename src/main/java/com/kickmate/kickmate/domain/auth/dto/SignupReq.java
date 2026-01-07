package com.kickmate.kickmate.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupReq {

    @NotBlank
    @Size(min = 4, max = 20)
    private String userId;

    @NotBlank
    @Size(min = 8, max = 64)
    private String password;
}
