package org.example.school.repository;

import org.example.school.entity.DossierAdministratif;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DossierAdministratifRepository extends JpaRepository<DossierAdministratif, Long> {
    Optional<DossierAdministratif> findByEleve_Id(Long eleveId);
}

