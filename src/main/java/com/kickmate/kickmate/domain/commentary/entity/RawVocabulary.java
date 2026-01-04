package com.kickmate.kickmate.domain.commentary.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "raw_vocabulary")
public class RawVocabulary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @Column(name = "type_name", length = 100)
    private String typeName;

    @Size(max = 100)
    @Column(name = "type_name_ko", length = 100)
    private String typeNameKo;


    @Column(name = "description")
    private String description;

}