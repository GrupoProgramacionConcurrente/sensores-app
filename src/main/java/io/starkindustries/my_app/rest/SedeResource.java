package io.starkindustries.my_app.rest;

import io.starkindustries.my_app.model.SedeDTO;
import io.starkindustries.my_app.service.SedeService;
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
@RequestMapping(value = "/api/sedes", produces = MediaType.APPLICATION_JSON_VALUE)
public class SedeResource {

    private final SedeService sedeService;

    public SedeResource(final SedeService sedeService) {
        this.sedeService = sedeService;
    }

    @GetMapping
    public ResponseEntity<List<SedeDTO>> getAllSedes() {
        return ResponseEntity.ok(sedeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SedeDTO> getSede(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(sedeService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSede(@RequestBody @Valid final SedeDTO sedeDTO) {
        final Long createdId = sedeService.create(sedeDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateSede(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final SedeDTO sedeDTO) {
        sedeService.update(id, sedeDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSede(@PathVariable(name = "id") final Long id) {
        sedeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
