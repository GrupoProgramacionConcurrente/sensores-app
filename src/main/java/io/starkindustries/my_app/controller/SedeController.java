package io.starkindustries.my_app.controller;

import io.starkindustries.my_app.domain.SistemaSeguridad;
import io.starkindustries.my_app.model.SedeDTO;
import io.starkindustries.my_app.repos.SistemaSeguridadRepository;
import io.starkindustries.my_app.service.SedeService;
import io.starkindustries.my_app.util.CustomCollectors;
import io.starkindustries.my_app.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/sedes")
public class SedeController {

    private final SedeService sedeService;
    private final SistemaSeguridadRepository sistemaSeguridadRepository;

    public SedeController(final SedeService sedeService,
            final SistemaSeguridadRepository sistemaSeguridadRepository) {
        this.sedeService = sedeService;
        this.sistemaSeguridadRepository = sistemaSeguridadRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("rel1Values", sistemaSeguridadRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(SistemaSeguridad::getId, SistemaSeguridad::getId)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("sedes", sedeService.findAll());
        return "sede/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("sede") final SedeDTO sedeDTO) {
        return "sede/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("sede") @Valid final SedeDTO sedeDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "sede/add";
        }
        sedeService.create(sedeDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("sede.create.success"));
        return "redirect:/sedes";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("sede", sedeService.get(id));
        return "sede/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("sede") @Valid final SedeDTO sedeDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "sede/edit";
        }
        sedeService.update(id, sedeDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("sede.update.success"));
        return "redirect:/sedes";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        sedeService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("sede.delete.success"));
        return "redirect:/sedes";
    }

}
