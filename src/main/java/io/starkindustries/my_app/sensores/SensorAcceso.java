package io.starkindustries.my_app.sensores;

import org.springframework.stereotype.Component;
import java.util.Random;

@Component
public class SensorAcceso {
    private Random random = new Random();

    public String detect() {
        int value = random.nextInt(500);
        if (value >= 400 && value < 450) {
            return "Acceso no autorizado detectado: Peligro Ligero";
        } else if (value >= 450 && value < 480) {
            return "Acceso no autorizado detectado: Peligro Medio";
        } else if (value >= 480) {
            return "Acceso no autorizado detectado: Peligro Grave";
        } else {
            return "Acceso no autorizado detectado: Sin Peligro";
        }
    }

}

