package com.kickmate.kickmate.domain.commentary.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "raw_filler_script")
public class RawFillerScript {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "game_id", nullable = false)
    private Long gameId;

    @NotNull
    @Lob
    @Column(name = "commentary", nullable = false)
    private String commentary;

}