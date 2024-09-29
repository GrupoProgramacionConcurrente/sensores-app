package io.starkindustries.my_app.service;

import io.starkindustries.my_app.sensores.SensorAcceso;
import io.starkindustries.my_app.sensores.SensorMovimiento;
import io.starkindustries.my_app.sensores.SensorTemperatura;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class SensorService {
    @Autowired
    private SensorMovimiento sensorMovimiento;

    @Autowired
    private SensorTemperatura sensorTemperatura;

    @Autowired
    private SensorAcceso sensorAcceso;

    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    @Async("taskExecutor")
    public void processSensorMovimiento() {
        String result = sensorMovimiento.detect();
        System.out.println(result);
    }

    @Async("taskExecutor")
    public void processTemperatureSensor() {
        String result = sensorTemperatura.detect();
        System.out.println(result);
    }

    @Async("taskExecutor")
    public void processAccessSensor() {
        String result = sensorAcceso.detect();
        System.out.println(result);
    }

    public CompletableFuture<String> detectarMovimiento() {
        return CompletableFuture.supplyAsync(() -> sensorMovimiento.detect(), executor);
    }

    public CompletableFuture<String> detectarTemperatura() {
        return CompletableFuture.supplyAsync(() -> sensorTemperatura.detect(), executor);
    }

    public CompletableFuture<String> detectarAcceso() {
        return CompletableFuture.supplyAsync(() -> sensorAcceso.detect(), executor);
    }
}
