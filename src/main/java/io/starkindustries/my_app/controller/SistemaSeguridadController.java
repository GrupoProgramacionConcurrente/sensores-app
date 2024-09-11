package io.starkindustries.my_app.controller;

import io.starkindustries.my_app.domain.SensorAcceso;
import io.starkindustries.my_app.domain.SensorMovimiento;
import io.starkindustries.my_app.domain.SensorTemperatura;
import io.starkindustries.my_app.domain.UsuarioSupervisor;
import io.starkindustries.my_app.model.SistemaSeguridadDTO;
import io.starkindustries.my_app.repos.SensorAccesoRepository;
import io.starkindustries.my_app.repos.SensorMovimientoRepository;
import io.starkindustries.my_app.repos.SensorTemperaturaRepository;
import io.starkindustries.my_app.repos.UsuarioSupervisorRepository;
import io.starkindustries.my_app.service.SistemaSeguridadService;
import io.starkindustries.my_app.util.CustomCollectors;
import io.starkindustries.my_app.util.ReferencedWarning;
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
@RequestMapping("/sistemaSeguridads")
public class SistemaSeguridadController {

    private final SistemaSeguridadService sistemaSeguridadService;
    private final SensorMovimientoRepository sensorMovimientoRepository;
    private final SensorTemperaturaRepository sensorTemperaturaRepository;
    private final SensorAccesoRepository sensorAccesoRepository;
    private final UsuarioSupervisorRepository usuarioSupervisorRepository;

    public SistemaSeguridadController(final SistemaSeguridadService sistemaSeguridadService,
            final SensorMovimientoRepository sensorMovimientoRepository,
            final SensorTemperaturaRepository sensorTemperaturaRepository,
            final SensorAccesoRepository sensorAccesoRepository,
            final UsuarioSupervisorRepository usuarioSupervisorRepository) {
        this.sistemaSeguridadService = sistemaSeguridadService;
        this.sensorMovimientoRepository = sensorMovimientoRepository;
        this.sensorTemperaturaRepository = sensorTemperaturaRepository;
        this.sensorAccesoRepository = sensorAccesoRepository;
        this.usuarioSupervisorRepository = usuarioSupervisorRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("rel2Values", sensorMovimientoRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(SensorMovimiento::getId, SensorMovimiento::getId)));
        model.addAttribute("rel3Values", sensorTemperaturaRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(SensorTemperatura::getId, SensorTemperatura::getId)));
        model.addAttribute("rel4Values", sensorAccesoRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(SensorAcceso::getId, SensorAcceso::getId)));
        model.addAttribute("rel12Values", usuarioSupervisorRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(UsuarioSupervisor::getId, UsuarioSupervisor::getId)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("sistemaSeguridads", sistemaSeguridadService.findAll());
        return "sistemaSeguridad/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("sistemaSeguridad") final SistemaSeguridadDTO sistemaSeguridadDTO) {
        return "sistemaSeguridad/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("sistemaSeguridad") @Valid final SistemaSeguridadDTO sistemaSeguridadDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "sistemaSeguridad/add";
        }
        sistemaSeguridadService.create(sistemaSeguridadDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("sistemaSeguridad.create.success"));
        return "redirect:/sistemaSeguridads";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("sistemaSeguridad", sistemaSeguridadService.get(id));
        return "sistemaSeguridad/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("sistemaSeguridad") @Valid final SistemaSeguridadDTO sistemaSeguridadDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "sistemaSeguridad/edit";
        }
        sistemaSeguridadService.update(id, sistemaSeguridadDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("sistemaSeguridad.update.success"));
        return "redirect:/sistemaSeguridads";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = sistemaSeguridadService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            sistemaSeguridadService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("sistemaSeguridad.delete.success"));
        }
        return "redirect:/sistemaSeguridads";
    }

}
