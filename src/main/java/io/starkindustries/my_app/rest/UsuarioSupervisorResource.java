package io.starkindustries.my_app.rest;

import io.starkindustries.my_app.model.UsuarioSupervisorDTO;
import io.starkindustries.my_app.service.UsuarioSupervisorService;
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
@RequestMapping(value = "/api/usuarioSupervisors", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioSupervisorResource {

    private final UsuarioSupervisorService usuarioSupervisorService;

    public UsuarioSupervisorResource(final UsuarioSupervisorService usuarioSupervisorService) {
        this.usuarioSupervisorService = usuarioSupervisorService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioSupervisorDTO>> getAllUsuarioSupervisors() {
        return ResponseEntity.ok(usuarioSupervisorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioSupervisorDTO> getUsuarioSupervisor(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(usuarioSupervisorService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createUsuarioSupervisor(
            @RequestBody @Valid final UsuarioSupervisorDTO usuarioSupervisorDTO) {
        final Long createdId = usuarioSupervisorService.create(usuarioSupervisorDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateUsuarioSupervisor(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final UsuarioSupervisorDTO usuarioSupervisorDTO) {
        usuarioSupervisorService.update(id, usuarioSupervisorDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUsuarioSupervisor(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = usuarioSupervisorService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        usuarioSupervisorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
