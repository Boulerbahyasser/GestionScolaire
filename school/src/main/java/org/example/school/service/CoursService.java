package org.example.school.service;

import lombok.RequiredArgsConstructor;
import org.example.school.entity.Cours;
import org.example.school.entity.Filiere;
import org.example.school.repository.CoursRepository;
import org.example.school.repository.FiliereRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoursService {

    private final CoursRepository coursRepository;
    private final FiliereRepository filiereRepository;

    public List<Cours> findAll() {
        return coursRepository.findAll();
    }

    public Cours findById(Long id) {
        return coursRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cours introuvable: " + id));
    }

    public List<Cours> findByFiliereId(Long filiereId) {
        return coursRepository.findByFiliere_Id(filiereId);
    }

    public Cours save(Cours cours) {
        Long filiereId = cours.getFiliere().getId();
        Filiere filiere = filiereRepository.findById(filiereId).orElseThrow();
        cours.setFiliere(filiere);
        return coursRepository.save(cours);
    }

    public void deleteById(Long id) {
        coursRepository.deleteById(id);
    }
}
