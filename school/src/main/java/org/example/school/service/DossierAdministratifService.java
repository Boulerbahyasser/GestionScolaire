package org.example.school.service;

import org.example.school.entity.DossierAdministratif;

public interface DossierAdministratifService {
    DossierAdministratif findByEleveId(Long eleveId);
}
