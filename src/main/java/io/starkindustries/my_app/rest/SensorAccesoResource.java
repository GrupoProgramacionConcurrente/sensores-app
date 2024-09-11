package io.starkindustries.my_app.rest;

import io.starkindustries.my_app.model.SensorAccesoDTO;
import io.starkindustries.my_app.service.SensorAccesoService;
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
@RequestMapping(value = "/api/sensorAccesos", produces = MediaType.APPLICATION_JSON_VALUE)
public class SensorAccesoResource {

    private final SensorAccesoService sensorAccesoService;

    public SensorAccesoResource(final SensorAccesoService sensorAccesoService) {
        this.sensorAccesoService = sensorAccesoService;
    }

    @GetMapping
    public ResponseEntity<List<SensorAccesoDTO>> getAllSensorAccesos() {
        return ResponseEntity.ok(sensorAccesoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorAccesoDTO> getSensorAcceso(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(sensorAccesoService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSensorAcceso(
            @RequestBody @Valid final SensorAccesoDTO sensorAccesoDTO) {
        final Long createdId = sensorAccesoService.create(sensorAccesoDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateSensorAcceso(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final SensorAccesoDTO sensorAccesoDTO) {
        sensorAccesoService.update(id, sensorAccesoDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSensorAcceso(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = sensorAccesoService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        sensorAccesoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
