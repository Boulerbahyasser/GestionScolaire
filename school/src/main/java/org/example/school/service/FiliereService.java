package org.example.school.service;

import org.example.school.entity.Filiere;
import java.util.List;

public interface FiliereService {
    List<Filiere> findAll();

    Filiere findById(Long id);

    Filiere save(Filiere filiere);

    void deleteById(Long id);
}
