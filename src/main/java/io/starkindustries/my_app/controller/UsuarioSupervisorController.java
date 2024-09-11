package io.starkindustries.my_app.controller;

import io.starkindustries.my_app.model.UsuarioSupervisorDTO;
import io.starkindustries.my_app.service.UsuarioSupervisorService;
import io.starkindustries.my_app.util.ReferencedWarning;
import io.starkindustries.my_app.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/usuarioSupervisors")
public class UsuarioSupervisorController {

    private final UsuarioSupervisorService usuarioSupervisorService;

    public UsuarioSupervisorController(final UsuarioSupervisorService usuarioSupervisorService) {
        this.usuarioSupervisorService = usuarioSupervisorService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("usuarioSupervisors", usuarioSupervisorService.findAll());
        return "usuarioSupervisor/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("usuarioSupervisor") final UsuarioSupervisorDTO usuarioSupervisorDTO) {
        return "usuarioSupervisor/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("usuarioSupervisor") @Valid final UsuarioSupervisorDTO usuarioSupervisorDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "usuarioSupervisor/add";
        }
        usuarioSupervisorService.create(usuarioSupervisorDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("usuarioSupervisor.create.success"));
        return "redirect:/usuarioSupervisors";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("usuarioSupervisor", usuarioSupervisorService.get(id));
        return "usuarioSupervisor/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("usuarioSupervisor") @Valid final UsuarioSupervisorDTO usuarioSupervisorDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "usuarioSupervisor/edit";
        }
        usuarioSupervisorService.update(id, usuarioSupervisorDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("usuarioSupervisor.update.success"));
        return "redirect:/usuarioSupervisors";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = usuarioSupervisorService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            usuarioSupervisorService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("usuarioSupervisor.delete.success"));
        }
        return "redirect:/usuarioSupervisors";
    }

}
