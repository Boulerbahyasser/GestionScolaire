package org.example.school.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.school.entity.Cours;
import org.example.school.entity.Filiere;
import org.example.school.repository.CoursRepository;
import org.example.school.repository.FiliereRepository;
import org.example.school.service.CoursService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoursServiceImpl implements CoursService {

    private final CoursRepository coursRepository;
    private final FiliereRepository filiereRepository;

    @Override
    public List<Cours> findAll() {
        return coursRepository.findAll();
    }

    @Override
    public Cours findById(Long id) {
        return coursRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cours introuvable: " + id));
    }

    @Override
    public List<Cours> findByFiliereId(Long filiereId) {
        return coursRepository.findByFiliere_Id(filiereId);
    }

    @Override
    public Cours save(Cours cours) {
        Long filiereId = cours.getFiliere().getId();
        Filiere filiere = filiereRepository.findById(filiereId).orElseThrow();
        cours.setFiliere(filiere);
        return coursRepository.save(cours);
    }

    @Override
    public void deleteById(Long id) {
        coursRepository.deleteById(id);
    }
}
