package org.example.school.service;

import lombok.RequiredArgsConstructor;
import org.example.school.entity.DossierAdministratif;
import org.example.school.repository.DossierAdministratifRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DossierAdministratifService {

    private final DossierAdministratifRepository dossierRepository;

    public DossierAdministratif findByEleveId(Long eleveId) {
        return dossierRepository.findByEleve_Id(eleveId).orElse(null);
    }
}
