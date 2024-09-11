package io.starkindustries.my_app.controller;

import io.starkindustries.my_app.domain.SistemaSeguridad;
import io.starkindustries.my_app.domain.UsuarioSupervisor;
import io.starkindustries.my_app.model.NotificacionDTO;
import io.starkindustries.my_app.repos.SistemaSeguridadRepository;
import io.starkindustries.my_app.repos.UsuarioSupervisorRepository;
import io.starkindustries.my_app.service.NotificacionService;
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
@RequestMapping("/notificacions")
public class NotificacionController {

    private final NotificacionService notificacionService;
    private final SistemaSeguridadRepository sistemaSeguridadRepository;
    private final UsuarioSupervisorRepository usuarioSupervisorRepository;

    public NotificacionController(final NotificacionService notificacionService,
            final SistemaSeguridadRepository sistemaSeguridadRepository,
            final UsuarioSupervisorRepository usuarioSupervisorRepository) {
        this.notificacionService = notificacionService;
        this.sistemaSeguridadRepository = sistemaSeguridadRepository;
        this.usuarioSupervisorRepository = usuarioSupervisorRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("rel9Values", sistemaSeguridadRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(SistemaSeguridad::getId, SistemaSeguridad::getId)));
        model.addAttribute("rel10Values", usuarioSupervisorRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(UsuarioSupervisor::getId, UsuarioSupervisor::getId)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("notificacions", notificacionService.findAll());
        return "notificacion/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("notificacion") final NotificacionDTO notificacionDTO) {
        return "notificacion/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("notificacion") @Valid final NotificacionDTO notificacionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "notificacion/add";
        }
        notificacionService.create(notificacionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("notificacion.create.success"));
        return "redirect:/notificacions";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("notificacion", notificacionService.get(id));
        return "notificacion/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("notificacion") @Valid final NotificacionDTO notificacionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "notificacion/edit";
        }
        notificacionService.update(id, notificacionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("notificacion.update.success"));
        return "redirect:/notificacions";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        notificacionService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("notificacion.delete.success"));
        return "redirect:/notificacions";
    }

}
