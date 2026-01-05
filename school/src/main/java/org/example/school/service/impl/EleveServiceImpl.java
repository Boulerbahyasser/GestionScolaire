package org.example.school.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.school.dto.EleveForm;
import org.example.school.entity.Cours;
import org.example.school.entity.DossierAdministratif;
import org.example.school.entity.Eleve;
import org.example.school.entity.Filiere;
import org.example.school.repository.CoursRepository;
import org.example.school.repository.DossierAdministratifRepository;
import org.example.school.repository.EleveRepository;
import org.example.school.repository.FiliereRepository;
import org.example.school.service.EleveService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EleveServiceImpl implements EleveService {

    private final EleveRepository eleveRepository;
    private final FiliereRepository filiereRepository;
    private final CoursRepository coursRepository;
    private final DossierAdministratifRepository dossierRepository;

    @Override
    public List<Eleve> findAll() {
        return eleveRepository.findAll();
    }

    @Override
    public Eleve findById(Long id) {
        return eleveRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Élève introuvable: " + id));
    }

    @Override
    @Transactional
    public Eleve createEleveWithDossier(EleveForm form) {
        Filiere filiere = filiereRepository.findById(form.getFiliereId())
                .orElseThrow(() -> new IllegalArgumentException("Filière introuvable: " + form.getFiliereId()));

        Set<Cours> selectedCours = mapCoursIdsToEntities(form.getCoursIds(), filiere.getId());

        Eleve eleve = Eleve.builder()
                .nom(form.getNom())
                .prenom(form.getPrenom())
                .filiere(filiere)
                .cours(selectedCours)
                .build();

        // 1) Save élève pour obtenir l'ID
        Eleve saved = eleveRepository.save(eleve);

        // 2) Générer numeroInscription = CODEFILIERE-ANNEE-ID
        int annee = LocalDate.now().getYear();
        String numero = filiere.getCode().toUpperCase() + "-" + annee + "-" + saved.getId();

        // 3) Créer le dossier
        DossierAdministratif dossier = DossierAdministratif.builder()
                .numeroInscription(numero)
                .dateCreation(LocalDate.now())
                .eleve(saved)
                .build();

        dossierRepository.save(dossier);

        return saved;
    }

    @Override
    @Transactional
    public Eleve updateEleve(Long id, EleveForm form) {
        Eleve eleve = findById(id);

        Filiere filiere = filiereRepository.findById(form.getFiliereId())
                .orElseThrow(() -> new IllegalArgumentException("Filière introuvable: " + form.getFiliereId()));

        Set<Cours> selectedCours = mapCoursIdsToEntities(form.getCoursIds(), filiere.getId());

        eleve.setNom(form.getNom());
        eleve.setPrenom(form.getPrenom());
        eleve.setFiliere(filiere);
        eleve.setCours(selectedCours);

        return eleveRepository.save(eleve);
    }

    @Override
    @Transactional
    public void deleteEleve(Long id) {
        dossierRepository.findByEleve_Id(id).ifPresent(dossierRepository::delete);
        eleveRepository.deleteById(id);
    }

    @Override
    public EleveForm toForm(Eleve eleve) {
        Set<Long> coursIds = new HashSet<>();
        if (eleve.getCours() != null) {
            for (Cours c : eleve.getCours())
                coursIds.add(c.getId());
        }

        return EleveForm.builder()
                .id(eleve.getId())
                .nom(eleve.getNom())
                .prenom(eleve.getPrenom())
                .filiereId(eleve.getFiliere() != null ? eleve.getFiliere().getId() : null)
                .coursIds(coursIds)
                .build();
    }

    private Set<Cours> mapCoursIdsToEntities(Set<Long> coursIds, Long filiereId) {
        Set<Cours> selected = new HashSet<>();
        if (coursIds == null)
            return selected;

        for (Long cid : coursIds) {
            Cours c = coursRepository.findById(cid)
                    .orElseThrow(() -> new IllegalArgumentException("Cours introuvable: " + cid));

            // Règle: un élève ne choisit que des cours de sa filière (sécurité)
            if (c.getFiliere() != null && !c.getFiliere().getId().equals(filiereId)) {
                throw new IllegalArgumentException("Cours " + c.getCode() + " n'appartient pas à la filière choisie");
            }

            selected.add(c);
        }
        return selected;
    }
}
