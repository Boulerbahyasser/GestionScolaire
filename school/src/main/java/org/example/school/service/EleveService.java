package org.example.school.service;

import org.example.school.dto.EleveForm;
import org.example.school.entity.Eleve;
import java.util.List;

public interface EleveService {
    List<Eleve> findAll();

    Eleve findById(Long id);

    Eleve createEleveWithDossier(EleveForm form);

    Eleve updateEleve(Long id, EleveForm form);

    void deleteEleve(Long id);

    EleveForm toForm(Eleve eleve);
}
