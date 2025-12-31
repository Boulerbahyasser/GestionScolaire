package org.example.school.service;

import lombok.RequiredArgsConstructor;
import org.example.school.entity.Filiere;
import org.example.school.repository.FiliereRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FiliereService {

    private final FiliereRepository filiereRepository;

    public List<Filiere> findAll() {
        return filiereRepository.findAll();
    }

    public Filiere findById(Long id) {
        return filiereRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Filière introuvable: " + id));
    }

    public Filiere save(Filiere filiere) {
        return filiereRepository.save(filiere);
    }

    public void deleteById(Long id) {
        filiereRepository.deleteById(id);
    }
}
