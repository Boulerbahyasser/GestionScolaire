package org.example.school.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Filiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String code;

    @NotBlank
    @Column(nullable = false)
    private String nom;

    @Builder.Default
    @OneToMany(mappedBy = "filiere", fetch = FetchType.LAZY)
    private Set<Eleve> eleves = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "filiere", fetch = FetchType.LAZY)
    private Set<Cours> cours = new HashSet<>();
}