package io.starkindustries.my_app.controller;

import io.starkindustries.my_app.model.SensorTemperaturaDTO;
import io.starkindustries.my_app.service.SensorTemperaturaService;
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
@RequestMapping("/sensorTemperaturas")
public class SensorTemperaturaController {

    private final SensorTemperaturaService sensorTemperaturaService;

    public SensorTemperaturaController(final SensorTemperaturaService sensorTemperaturaService) {
        this.sensorTemperaturaService = sensorTemperaturaService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("sensorTemperaturas", sensorTemperaturaService.findAll());
        return "sensorTemperatura/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("sensorTemperatura") final SensorTemperaturaDTO sensorTemperaturaDTO) {
        return "sensorTemperatura/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("sensorTemperatura") @Valid final SensorTemperaturaDTO sensorTemperaturaDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "sensorTemperatura/add";
        }
        sensorTemperaturaService.create(sensorTemperaturaDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("sensorTemperatura.create.success"));
        return "redirect:/sensorTemperaturas";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("sensorTemperatura", sensorTemperaturaService.get(id));
        return "sensorTemperatura/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("sensorTemperatura") @Valid final SensorTemperaturaDTO sensorTemperaturaDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "sensorTemperatura/edit";
        }
        sensorTemperaturaService.update(id, sensorTemperaturaDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("sensorTemperatura.update.success"));
        return "redirect:/sensorTemperaturas";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = sensorTemperaturaService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            sensorTemperaturaService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("sensorTemperatura.delete.success"));
        }
        return "redirect:/sensorTemperaturas";
    }

}
