package io.starkindustries.my_app.rest;

import io.starkindustries.my_app.model.EventoDTO;
import io.starkindustries.my_app.service.EventoService;
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
@RequestMapping(value = "/api/eventos", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventoResource {

    private final EventoService eventoService;

    public EventoResource(final EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public ResponseEntity<List<EventoDTO>> getAllEventos() {
        return ResponseEntity.ok(eventoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> getEvento(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(eventoService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createEvento(@RequestBody @Valid final EventoDTO eventoDTO) {
        final Long createdId = eventoService.create(eventoDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateEvento(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final EventoDTO eventoDTO) {
        eventoService.update(id, eventoDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEvento(@PathVariable(name = "id") final Long id) {
        eventoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
