package org.example.school.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.school.entity.Cours;
import org.example.school.service.CoursService;
import org.example.school.service.FiliereService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cours")
@RequiredArgsConstructor
public class CoursController {

    private final CoursService coursService;
    private final FiliereService filiereService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("coursList", coursService.findAll());
        return "cours/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("cours", new Cours());
        model.addAttribute("filieres", filiereService.findAll());
        model.addAttribute("selectedFiliereId", null);
        model.addAttribute("mode", "create");
        return "cours/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("cours") Cours cours,
                         BindingResult br,
                         @RequestParam(required = false) Long filiereId,
                         Model model) {
        if (br.hasErrors()) {
            model.addAttribute("filieres", filiereService.findAll());
            model.addAttribute("selectedFiliereId", filiereId);
            model.addAttribute("mode", "create");
            return "cours/form";
        }
        coursService.save(cours);
        return "redirect:/cours";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Cours cours = coursService.findById(id);
        Long selected = (cours.getFiliere() != null) ? cours.getFiliere().getId() : null;

        model.addAttribute("cours", cours);
        model.addAttribute("filieres", filiereService.findAll());
        model.addAttribute("selectedFiliereId", selected);
        model.addAttribute("mode", "edit");
        return "cours/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("cours") Cours cours,
                         BindingResult br,
                         @RequestParam(required = false) Long filiereId,
                         Model model) {
        if (br.hasErrors()) {
            model.addAttribute("filieres", filiereService.findAll());
            model.addAttribute("selectedFiliereId", filiereId);
            model.addAttribute("mode", "edit");
            return "cours/form";
        }
        cours.setId(id);
        coursService.save(cours);
        return "redirect:/cours";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        coursService.deleteById(id);
        return "redirect:/cours";
    }
}
