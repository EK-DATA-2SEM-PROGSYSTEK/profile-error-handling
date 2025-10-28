package ek.ianb.profileerrorhandling.controller;

import ek.ianb.profileerrorhandling.model.Profile;
import ek.ianb.profileerrorhandling.service.ProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("profiles", service.list());
        return "profiles/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("profile", new Profile());
        model.addAttribute("mode", "create");
        return "profiles/form";
    }

    @PostMapping
    public String create(@ModelAttribute Profile profile, Model model) {
        service.create(profile);
        return "redirect:/profiles";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable int id, Model model) {
        model.addAttribute("profile", service.get(id));
        model.addAttribute("mode", "edit");
        return "profiles/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable int id, @ModelAttribute Profile profile) {
        service.update(id, profile);
        return "redirect:/profiles";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable int id) {
        service.delete(id);
        return "redirect:/profiles";
    }
}
