package io.starkindustries.my_app.sensores;

import org.springframework.stereotype.Component;
import java.util.Random;

@Component
public class SensorTemperatura  {
    private Random random = new Random();

    public String detect() {
        int value = random.nextInt(500);
        if (value >= 400 && value < 450) {
            return "Temperatura elevada detectada: Peligro Ligero";
        } else if (value >= 450 && value < 480) {
            return "Temperatura elevada detectada: Peligro Medio";
        } else if (value >= 480) {
            return "Temperatura elevada detectada: Peligro Grave";
        } else {
            return "Temperatura elevada detectada: Sin Peligro";
        }
    }

}
