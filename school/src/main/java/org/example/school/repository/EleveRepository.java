package org.example.school.repository;

import org.example.school.entity.Eleve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EleveRepository extends JpaRepository<Eleve, Long> {
    List<Eleve> findByFiliere_Id(Long filiereId);
    List<Eleve> findByCours_Id(Long IdCours);
}
