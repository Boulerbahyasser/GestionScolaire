package org.example.school.service;

import org.example.school.entity.Cours;
import java.util.List;

public interface CoursService {
    List<Cours> findAll();

    Cours findById(Long id);

    List<Cours> findByFiliereId(Long filiereId);

    Cours save(Cours cours);

    void deleteById(Long id);
}
