package io.starkindustries.my_app.rest;

import io.starkindustries.my_app.model.RespuestaDTO;
import io.starkindustries.my_app.service.RespuestaService;
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
@RequestMapping(value = "/api/respuestas", produces = MediaType.APPLICATION_JSON_VALUE)
public class RespuestaResource {

    private final RespuestaService respuestaService;

    public RespuestaResource(final RespuestaService respuestaService) {
        this.respuestaService = respuestaService;
    }

    @GetMapping
    public ResponseEntity<List<RespuestaDTO>> getAllRespuestas() {
        return ResponseEntity.ok(respuestaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaDTO> getRespuesta(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(respuestaService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createRespuesta(
            @RequestBody @Valid final RespuestaDTO respuestaDTO) {
        final Long createdId = respuestaService.create(respuestaDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateRespuesta(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final RespuestaDTO respuestaDTO) {
        respuestaService.update(id, respuestaDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRespuesta(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = respuestaService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        respuestaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
