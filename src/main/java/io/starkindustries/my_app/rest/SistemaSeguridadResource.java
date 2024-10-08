package io.starkindustries.my_app.rest;

import io.starkindustries.my_app.model.SistemaSeguridadDTO;
import io.starkindustries.my_app.service.SistemaSeguridadService;
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
@RequestMapping(value = "/api/sistemaSeguridads", produces = MediaType.APPLICATION_JSON_VALUE)
public class SistemaSeguridadResource {

    private final SistemaSeguridadService sistemaSeguridadService;

    public SistemaSeguridadResource(final SistemaSeguridadService sistemaSeguridadService) {
        this.sistemaSeguridadService = sistemaSeguridadService;
    }

    @GetMapping
    public ResponseEntity<List<SistemaSeguridadDTO>> getAllSistemaSeguridads() {
        return ResponseEntity.ok(sistemaSeguridadService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SistemaSeguridadDTO> getSistemaSeguridad(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(sistemaSeguridadService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSistemaSeguridad(
            @RequestBody @Valid final SistemaSeguridadDTO sistemaSeguridadDTO) {
        final Long createdId = sistemaSeguridadService.create(sistemaSeguridadDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateSistemaSeguridad(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final SistemaSeguridadDTO sistemaSeguridadDTO) {
        sistemaSeguridadService.update(id, sistemaSeguridadDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSistemaSeguridad(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = sistemaSeguridadService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        sistemaSeguridadService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
