package org.example.school.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.school.entity.DossierAdministratif;
import org.example.school.entity.Eleve;
import org.example.school.repository.DossierAdministratifRepository;
import org.example.school.service.CoursService;
import org.example.school.service.EleveService;
import org.example.school.service.FiliereService;
import org.example.school.dto.EleveForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/eleves")
@RequiredArgsConstructor
public class EleveController {

    private final EleveService eleveService;
    private final FiliereService filiereService;
    private final CoursService coursService;
    private final DossierAdministratifRepository dossierRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("eleves", eleveService.findAll());
        return "eleves/list";
    }

    @GetMapping("/new")
    public String createForm(@ModelAttribute("eleveForm") EleveForm form,
            @RequestParam(name = "loadCours", defaultValue = "false") boolean loadCours,
            Model model) {

        model.addAttribute("mode", "create");
        model.addAttribute("filieres", filiereService.findAll());

        boolean showCours = (loadCours || form.getFiliereId() != null) && form.getFiliereId() != null;
        model.addAttribute("showCours", showCours);

        if (showCours) {
            model.addAttribute("coursList", coursService.findByFiliereId(form.getFiliereId()));
        } else {
            model.addAttribute("coursList", List.of());
        }

        return "eleves/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("eleveForm") EleveForm form,
            BindingResult br,
            Model model) {

        if (br.hasErrors()) {
            model.addAttribute("filieres", filiereService.findAll());
            model.addAttribute("coursList",
                    form.getFiliereId() != null ? coursService.findByFiliereId(form.getFiliereId()) : List.of());
            model.addAttribute("showCours", form.getFiliereId() != null);
            model.addAttribute("mode", "create");
            return "eleves/form";
        }

        eleveService.createEleveWithDossier(form);
        return "redirect:/eleves";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        Eleve eleve = eleveService.findById(id);
        DossierAdministratif dossier = dossierRepository.findByEleve_Id(id).orElse(null);

        model.addAttribute("eleve", eleve);
        model.addAttribute("dossier", dossier);
        return "eleves/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Eleve eleve = eleveService.findById(id);
        EleveForm form = eleveService.toForm(eleve);

        model.addAttribute("eleveForm", form);
        model.addAttribute("filieres", filiereService.findAll());

        model.addAttribute("coursList",
                form.getFiliereId() != null ? coursService.findByFiliereId(form.getFiliereId()) : List.of());

        model.addAttribute("mode", "edit");
        model.addAttribute("showCours", form.getFiliereId() != null);
        return "eleves/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
            @Valid @ModelAttribute("eleveForm") EleveForm form,
            BindingResult br,
            Model model) {
        if (br.hasErrors()) {
            model.addAttribute("filieres", filiereService.findAll());
            model.addAttribute("coursList", coursService.findAll());
            model.addAttribute("mode", "edit");
            return "eleves/form";
        }
        eleveService.updateEleve(id, form);
        return "redirect:/eleves";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        eleveService.deleteEleve(id);
        return "redirect:/eleves";
    }
}
