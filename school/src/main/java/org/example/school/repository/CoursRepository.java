package org.example.school.repository;

import org.example.school.entity.Cours;
import org.example.school.entity.Eleve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoursRepository extends JpaRepository<Cours, Long> {
    Optional<Cours> findByCodeIgnoreCase(String code);
    List<Cours> findByFiliere_Id(Long filiereId);

}
