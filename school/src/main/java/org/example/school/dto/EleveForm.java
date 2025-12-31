package org.example.school.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class EleveForm {

    private Long id;

    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    @NotNull
    private Long filiereId;

    @Builder.Default
    private Set<Long> coursIds = new HashSet<>();
}
