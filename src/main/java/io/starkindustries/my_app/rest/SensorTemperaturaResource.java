package io.starkindustries.my_app.rest;

import io.starkindustries.my_app.model.SensorTemperaturaDTO;
import io.starkindustries.my_app.service.SensorTemperaturaService;
import io.starkindustries.my_app.util.ReferencedException;
import io.starkindustries.my_app.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/sensorTemperaturas", produces = MediaType.APPLICATION_JSON_VALUE)
public class SensorTemperaturaResource {

    private final SensorTemperaturaService sensorTemperaturaService;

    public SensorTemperaturaResource(final SensorTemperaturaService sensorTemperaturaService) {
        this.sensorTemperaturaService = sensorTemperaturaService;
    }

    @GetMapping
    public ResponseEntity<List<SensorTemperaturaDTO>> getAllSensorTemperaturas() {
        return ResponseEntity.ok(sensorTemperaturaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorTemperaturaDTO> getSensorTemperatura(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(sensorTemperaturaService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSensorTemperatura(
            @RequestBody @Valid final SensorTemperaturaDTO sensorTemperaturaDTO) {
        final Long createdId = sensorTemperaturaService.create(sensorTemperaturaDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateSensorTemperatura(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final SensorTemperaturaDTO sensorTemperaturaDTO) {
        sensorTemperaturaService.update(id, sensorTemperaturaDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSensorTemperatura(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = sensorTemperaturaService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        sensorTemperaturaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
