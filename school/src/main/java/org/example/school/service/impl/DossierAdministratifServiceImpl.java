package org.example.school.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.school.entity.DossierAdministratif;
import org.example.school.repository.DossierAdministratifRepository;
import org.example.school.service.DossierAdministratifService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DossierAdministratifServiceImpl implements DossierAdministratifService {

    private final DossierAdministratifRepository dossierRepository;

    @Override
    public DossierAdministratif findByEleveId(Long eleveId) {
        return dossierRepository.findByEleve_Id(eleveId).orElse(null);
    }
}
