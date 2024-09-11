package io.starkindustries.my_app.service;

import io.starkindustries.my_app.domain.Notificacion;
import io.starkindustries.my_app.domain.SistemaSeguridad;
import io.starkindustries.my_app.domain.UsuarioSupervisor;
import io.starkindustries.my_app.model.NotificacionDTO;
import io.starkindustries.my_app.repos.NotificacionRepository;
import io.starkindustries.my_app.repos.SistemaSeguridadRepository;
import io.starkindustries.my_app.repos.UsuarioSupervisorRepository;
import io.starkindustries.my_app.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final SistemaSeguridadRepository sistemaSeguridadRepository;
    private final UsuarioSupervisorRepository usuarioSupervisorRepository;

    public NotificacionService(final NotificacionRepository notificacionRepository,
            final SistemaSeguridadRepository sistemaSeguridadRepository,
            final UsuarioSupervisorRepository usuarioSupervisorRepository) {
        this.notificacionRepository = notificacionRepository;
        this.sistemaSeguridadRepository = sistemaSeguridadRepository;
        this.usuarioSupervisorRepository = usuarioSupervisorRepository;
    }

    public List<NotificacionDTO> findAll() {
        final List<Notificacion> notificacions = notificacionRepository.findAll(Sort.by("id"));
        return notificacions.stream()
                .map(notificacion -> mapToDTO(notificacion, new NotificacionDTO()))
                .toList();
    }

    public NotificacionDTO get(final Long id) {
        return notificacionRepository.findById(id)
                .map(notificacion -> mapToDTO(notificacion, new NotificacionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final NotificacionDTO notificacionDTO) {
        final Notificacion notificacion = new Notificacion();
        mapToEntity(notificacionDTO, notificacion);
        return notificacionRepository.save(notificacion).getId();
    }

    public void update(final Long id, final NotificacionDTO notificacionDTO) {
        final Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(notificacionDTO, notificacion);
        notificacionRepository.save(notificacion);
    }

    public void delete(final Long id) {
        notificacionRepository.deleteById(id);
    }

    private NotificacionDTO mapToDTO(final Notificacion notificacion,
            final NotificacionDTO notificacionDTO) {
        notificacionDTO.setId(notificacion.getId());
        notificacionDTO.setMensaje(notificacion.getMensaje());
        notificacionDTO.setFechaHoraEnvio(notificacion.getFechaHoraEnvio());
        notificacionDTO.setDestinatario(notificacion.getDestinatario());
        notificacionDTO.setTipoNotificacion(notificacion.getTipoNotificacion());
        notificacionDTO.setRel9(notificacion.getRel9() == null ? null : notificacion.getRel9().getId());
        notificacionDTO.setRel10(notificacion.getRel10().stream()
                .map(usuarioSupervisor -> usuarioSupervisor.getId())
                .toList());
        return notificacionDTO;
    }

    private Notificacion mapToEntity(final NotificacionDTO notificacionDTO,
            final Notificacion notificacion) {
        notificacion.setMensaje(notificacionDTO.getMensaje());
        notificacion.setFechaHoraEnvio(notificacionDTO.getFechaHoraEnvio());
        notificacion.setDestinatario(notificacionDTO.getDestinatario());
        notificacion.setTipoNotificacion(notificacionDTO.getTipoNotificacion());
        final SistemaSeguridad rel9 = notificacionDTO.getRel9() == null ? null : sistemaSeguridadRepository.findById(notificacionDTO.getRel9())
                .orElseThrow(() -> new NotFoundException("rel9 not found"));
        notificacion.setRel9(rel9);
        final List<UsuarioSupervisor> rel10 = usuarioSupervisorRepository.findAllById(
                notificacionDTO.getRel10() == null ? Collections.emptyList() : notificacionDTO.getRel10());
        if (rel10.size() != (notificacionDTO.getRel10() == null ? 0 : notificacionDTO.getRel10().size())) {
            throw new NotFoundException("one of rel10 not found");
        }
        notificacion.setRel10(new HashSet<>(rel10));
        return notificacion;
    }

}
