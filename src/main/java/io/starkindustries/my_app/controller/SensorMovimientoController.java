package io.starkindustries.my_app.controller;

import io.starkindustries.my_app.model.SensorMovimientoDTO;
import io.starkindustries.my_app.service.SensorMovimientoService;
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
@RequestMapping("/sensorMovimientos")
public class SensorMovimientoController {

    private final SensorMovimientoService sensorMovimientoService;

    public SensorMovimientoController(final SensorMovimientoService sensorMovimientoService) {
        this.sensorMovimientoService = sensorMovimientoService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("sensorMovimientoes", sensorMovimientoService.findAll());
        return "sensorMovimiento/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("sensorMovimiento") final SensorMovimientoDTO sensorMovimientoDTO) {
        return "sensorMovimiento/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("sensorMovimiento") @Valid final SensorMovimientoDTO sensorMovimientoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "sensorMovimiento/add";
        }
        sensorMovimientoService.create(sensorMovimientoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("sensorMovimiento.create.success"));
        return "redirect:/sensorMovimientos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("sensorMovimiento", sensorMovimientoService.get(id));
        return "sensorMovimiento/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("sensorMovimiento") @Valid final SensorMovimientoDTO sensorMovimientoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "sensorMovimiento/edit";
        }
        sensorMovimientoService.update(id, sensorMovimientoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("sensorMovimiento.update.success"));
        return "redirect:/sensorMovimientos";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = sensorMovimientoService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            sensorMovimientoService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("sensorMovimiento.delete.success"));
        }
        return "redirect:/sensorMovimientos";
    }

}
