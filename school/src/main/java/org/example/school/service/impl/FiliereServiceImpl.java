package org.example.school.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.school.entity.Filiere;
import org.example.school.repository.FiliereRepository;
import org.example.school.service.FiliereService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FiliereServiceImpl implements FiliereService {

    private final FiliereRepository filiereRepository;

    @Override
    public List<Filiere> findAll() {
        return filiereRepository.findAll();
    }

    @Override
    public Filiere findById(Long id) {
        return filiereRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Filière introuvable: " + id));
    }

    @Override
    public Filiere save(Filiere filiere) {
        return filiereRepository.save(filiere);
    }

    @Override
    public void deleteById(Long id) {
        filiereRepository.deleteById(id);
    }
}
