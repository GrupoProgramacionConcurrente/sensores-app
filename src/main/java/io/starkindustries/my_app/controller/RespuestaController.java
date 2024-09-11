package io.starkindustries.my_app.controller;

import io.starkindustries.my_app.model.RespuestaDTO;
import io.starkindustries.my_app.service.RespuestaService;
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
@RequestMapping("/respuestas")
public class RespuestaController {

    private final RespuestaService respuestaService;

    public RespuestaController(final RespuestaService respuestaService) {
        this.respuestaService = respuestaService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("respuestas", respuestaService.findAll());
        return "respuesta/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("respuesta") final RespuestaDTO respuestaDTO) {
        return "respuesta/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("respuesta") @Valid final RespuestaDTO respuestaDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "respuesta/add";
        }
        respuestaService.create(respuestaDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("respuesta.create.success"));
        return "redirect:/respuestas";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("respuesta", respuestaService.get(id));
        return "respuesta/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("respuesta") @Valid final RespuestaDTO respuestaDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "respuesta/edit";
        }
        respuestaService.update(id, respuestaDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("respuesta.update.success"));
        return "redirect:/respuestas";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = respuestaService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            respuestaService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("respuesta.delete.success"));
        }
        return "redirect:/respuestas";
    }

}
