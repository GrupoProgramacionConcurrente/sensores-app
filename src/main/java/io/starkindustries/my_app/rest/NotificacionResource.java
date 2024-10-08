package io.starkindustries.my_app.rest;

import io.starkindustries.my_app.model.NotificacionDTO;
import io.starkindustries.my_app.service.NotificacionService;
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
@RequestMapping(value = "/api/notificacions", produces = MediaType.APPLICATION_JSON_VALUE)
public class NotificacionResource {

    private final NotificacionService notificacionService;

    public NotificacionResource(final NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @GetMapping
    public ResponseEntity<List<NotificacionDTO>> getAllNotificacions() {
        return ResponseEntity.ok(notificacionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacionDTO> getNotificacion(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(notificacionService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createNotificacion(
            @RequestBody @Valid final NotificacionDTO notificacionDTO) {
        final Long createdId = notificacionService.create(notificacionDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateNotificacion(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final NotificacionDTO notificacionDTO) {
        notificacionService.update(id, notificacionDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteNotificacion(@PathVariable(name = "id") final Long id) {
        notificacionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
