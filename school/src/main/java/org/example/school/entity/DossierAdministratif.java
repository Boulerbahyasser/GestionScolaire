package org.example.school.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class DossierAdministratif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroInscription;

    @Column(nullable = false)
    private LocalDate dateCreation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eleve_id", unique = true, nullable = false)
    private Eleve eleve;
}
