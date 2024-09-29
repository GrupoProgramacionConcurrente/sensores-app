package io.starkindustries.my_app.sensores;

import org.springframework.stereotype.Component;
import java.util.Random;

@Component
public class SensorMovimiento {
    private Random random = new Random();

    public String detect() {
        int value = random.nextInt(500);
        if (value >= 400 && value < 450) {
            return "Movimiento detectado: Peligro Ligero";
        } else if (value >= 450 && value < 480) {
            return "Movimiento detectado: Peligro Medio";
        } else if (value >= 480) {
            return "Movimiento detectado: Peligro Grave";
        } else {
            return "Movimiento detectado: Sin Peligro";
        }
    }

}
