package io.starkindustries.my_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/servers")
public class ServerController {

    @GetMapping
    public String getServers(Model model) {
        List<String> servers = Arrays.asList("Server1", "Server2", "Server3"); // Aquí iría tu lógica real para obtener los servidores
        model.addAttribute("servers", servers);

        // Return the name of the view to be rendered
        return "servers/serverSelection";
    }

    @PostMapping("/connect")
    public String connectToServer(@RequestParam String server, Model model) {
        // Aquí puedes manejar la lógica para conectar al servidor seleccionado
        return "redirect:http://" + server;  // Redirigir al servidor elegido
    }
}
