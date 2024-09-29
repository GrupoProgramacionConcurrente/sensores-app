package io.starkindustries.my_app.controller;

import io.starkindustries.my_app.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping
    public String getSensors(Model model) {
        List<String> sensors = Arrays.asList("SensorAcceso", "SensorMovimiento", "SensorTemperatura");
        model.addAttribute("sensors", sensors);
        return "sensors/sensorSelection";
    }

    @PostMapping("/connect")
    public String connectToSensor(@RequestParam String sensor, Model model) {
        // Logic to handle sensor connection
        return "redirect:/" + sensor.toLowerCase();  // Redirect to the selected sensor page
    }

    @GetMapping("/sensors/detectar-movimiento")
    @ResponseBody
    public String detectarMovimiento() throws Exception {
        return sensorService.detectarMovimiento().get();
    }

    @GetMapping("/sensors/detectar-temperatura")
    @ResponseBody
    public String detectarTemperatura() throws Exception {
        return sensorService.detectarTemperatura().get();
    }

    @GetMapping("/sensors/detectar-acceso")
    @ResponseBody
    public String detectarAcceso() throws Exception {
        return sensorService.detectarAcceso().get();
    }
}
