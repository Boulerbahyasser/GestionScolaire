package org.example.school.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.school.entity.Filiere;
import org.example.school.service.FiliereService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/filieres")
@RequiredArgsConstructor
public class FiliereController {

    private final FiliereService filiereService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("filieres", filiereService.findAll());
        return "filieres/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("filiere", new Filiere());
        model.addAttribute("mode", "create");
        return "filieres/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("filiere") Filiere filiere,
                         BindingResult br,
                         Model model) {
        if (br.hasErrors()) {
            model.addAttribute("mode", "create");
            return "filieres/form";
        }
        filiereService.save(filiere);
        return "redirect:/filieres";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("filiere", filiereService.findById(id));
        return "filieres/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("filiere", filiereService.findById(id));
        model.addAttribute("mode", "edit");
        return "filieres/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("filiere") Filiere filiere,
                         BindingResult br,
                         Model model) {
        if (br.hasErrors()) {
            model.addAttribute("mode", "edit");
            return "filieres/form";
        }
        filiere.setId(id);
        filiereService.save(filiere);
        return "redirect:/filieres";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        filiereService.deleteById(id);
        return "redirect:/filieres";
    }
}
