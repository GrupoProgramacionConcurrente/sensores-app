package io.starkindustries.my_app.controller;

import io.starkindustries.my_app.domain.Respuesta;
import io.starkindustries.my_app.domain.SensorAcceso;
import io.starkindustries.my_app.domain.SensorMovimiento;
import io.starkindustries.my_app.domain.SensorTemperatura;
import io.starkindustries.my_app.domain.UsuarioSupervisor;
import io.starkindustries.my_app.model.EventoDTO;
import io.starkindustries.my_app.repos.RespuestaRepository;
import io.starkindustries.my_app.repos.SensorAccesoRepository;
import io.starkindustries.my_app.repos.SensorMovimientoRepository;
import io.starkindustries.my_app.repos.SensorTemperaturaRepository;
import io.starkindustries.my_app.repos.UsuarioSupervisorRepository;
import io.starkindustries.my_app.service.EventoService;
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
@RequestMapping("/eventos")
public class EventoController {

    private final EventoService eventoService;
    private final SensorMovimientoRepository sensorMovimientoRepository;
    private final SensorTemperaturaRepository sensorTemperaturaRepository;
    private final SensorAccesoRepository sensorAccesoRepository;
    private final UsuarioSupervisorRepository usuarioSupervisorRepository;
    private final RespuestaRepository respuestaRepository;

    public EventoController(final EventoService eventoService,
            final SensorMovimientoRepository sensorMovimientoRepository,
            final SensorTemperaturaRepository sensorTemperaturaRepository,
            final SensorAccesoRepository sensorAccesoRepository,
            final UsuarioSupervisorRepository usuarioSupervisorRepository,
            final RespuestaRepository respuestaRepository) {
        this.eventoService = eventoService;
        this.sensorMovimientoRepository = sensorMovimientoRepository;
        this.sensorTemperaturaRepository = sensorTemperaturaRepository;
        this.sensorAccesoRepository = sensorAccesoRepository;
        this.usuarioSupervisorRepository = usuarioSupervisorRepository;
        this.respuestaRepository = respuestaRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("rel5Values", sensorMovimientoRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(SensorMovimiento::getId, SensorMovimiento::getId)));
        model.addAttribute("rel6Values", sensorTemperaturaRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(SensorTemperatura::getId, SensorTemperatura::getId)));
        model.addAttribute("rel7Values", sensorAccesoRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(SensorAcceso::getId, SensorAcceso::getId)));
        model.addAttribute("rel8Values", usuarioSupervisorRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(UsuarioSupervisor::getId, UsuarioSupervisor::getId)));
        model.addAttribute("rel11Values", respuestaRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Respuesta::getId, Respuesta::getId)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("eventoes", eventoService.findAll());
        return "evento/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("evento") final EventoDTO eventoDTO) {
        return "evento/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("evento") @Valid final EventoDTO eventoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "evento/add";
        }
        eventoService.create(eventoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("evento.create.success"));
        return "redirect:/eventos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("evento", eventoService.get(id));
        return "evento/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("evento") @Valid final EventoDTO eventoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "evento/edit";
        }
        eventoService.update(id, eventoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("evento.update.success"));
        return "redirect:/eventos";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        eventoService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("evento.delete.success"));
        return "redirect:/eventos";
    }

}
