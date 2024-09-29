package io.starkindustries.my_app.sensores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.CommandLineRunner;
import io.starkindustries.my_app.service.SensorService;

@SpringBootApplication
@ComponentScan(basePackages = {"io.starkindustries.my_app.sensores", "io.starkindustries.my_app.service", "io.starkindustries.my_app.controller"})
public class SensorApplication implements CommandLineRunner {

    @Autowired
    private SensorService sensorService;

    public static void main(String[] args) {
        SpringApplication.run(SensorApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        sensorService.processSensorMovimiento();
        sensorService.processTemperatureSensor();
        sensorService.processAccessSensor();
        // Espera 5 segundos para que los hilos asíncronos terminen
        Thread.sleep(5000);
    }
}
