package io.starkindustries.my_app.service;

import io.starkindustries.my_app.domain.Evento;
import io.starkindustries.my_app.domain.SistemaSeguridad;
import io.starkindustries.my_app.domain.UsuarioSupervisor;
import io.starkindustries.my_app.model.UsuarioSupervisorDTO;
import io.starkindustries.my_app.repos.EventoRepository;
import io.starkindustries.my_app.repos.NotificacionRepository;
import io.starkindustries.my_app.repos.SistemaSeguridadRepository;
import io.starkindustries.my_app.repos.UsuarioSupervisorRepository;
import io.starkindustries.my_app.util.NotFoundException;
import io.starkindustries.my_app.util.ReferencedWarning;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class UsuarioSupervisorService {

    private final UsuarioSupervisorRepository usuarioSupervisorRepository;
    private final NotificacionRepository notificacionRepository;
    private final EventoRepository eventoRepository;
    private final SistemaSeguridadRepository sistemaSeguridadRepository;

    public UsuarioSupervisorService(final UsuarioSupervisorRepository usuarioSupervisorRepository,
            final NotificacionRepository notificacionRepository,
            final EventoRepository eventoRepository,
            final SistemaSeguridadRepository sistemaSeguridadRepository) {
        this.usuarioSupervisorRepository = usuarioSupervisorRepository;
        this.notificacionRepository = notificacionRepository;
        this.eventoRepository = eventoRepository;
        this.sistemaSeguridadRepository = sistemaSeguridadRepository;
    }

    public List<UsuarioSupervisorDTO> findAll() {
        final List<UsuarioSupervisor> usuarioSupervisors = usuarioSupervisorRepository.findAll(Sort.by("id"));
        return usuarioSupervisors.stream()
                .map(usuarioSupervisor -> mapToDTO(usuarioSupervisor, new UsuarioSupervisorDTO()))
                .toList();
    }

    public UsuarioSupervisorDTO get(final Long id) {
        return usuarioSupervisorRepository.findById(id)
                .map(usuarioSupervisor -> mapToDTO(usuarioSupervisor, new UsuarioSupervisorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UsuarioSupervisorDTO usuarioSupervisorDTO) {
        final UsuarioSupervisor usuarioSupervisor = new UsuarioSupervisor();
        mapToEntity(usuarioSupervisorDTO, usuarioSupervisor);
        return usuarioSupervisorRepository.save(usuarioSupervisor).getId();
    }

    public void update(final Long id, final UsuarioSupervisorDTO usuarioSupervisorDTO) {
        final UsuarioSupervisor usuarioSupervisor = usuarioSupervisorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(usuarioSupervisorDTO, usuarioSupervisor);
        usuarioSupervisorRepository.save(usuarioSupervisor);
    }

    public void delete(final Long id) {
        final UsuarioSupervisor usuarioSupervisor = usuarioSupervisorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        notificacionRepository.findAllByRel10(usuarioSupervisor)
                .forEach(notificacion -> notificacion.getRel10().remove(usuarioSupervisor));
        usuarioSupervisorRepository.delete(usuarioSupervisor);
    }

    private UsuarioSupervisorDTO mapToDTO(final UsuarioSupervisor usuarioSupervisor,
            final UsuarioSupervisorDTO usuarioSupervisorDTO) {
        usuarioSupervisorDTO.setId(usuarioSupervisor.getId());
        usuarioSupervisorDTO.setNombre(usuarioSupervisor.getNombre());
        usuarioSupervisorDTO.setCredenciales(usuarioSupervisor.getCredenciales());
        usuarioSupervisorDTO.setRol(usuarioSupervisor.getRol());
        usuarioSupervisorDTO.setNivelAcceso(usuarioSupervisor.getNivelAcceso());
        return usuarioSupervisorDTO;
    }

    private UsuarioSupervisor mapToEntity(final UsuarioSupervisorDTO usuarioSupervisorDTO,
            final UsuarioSupervisor usuarioSupervisor) {
        usuarioSupervisor.setNombre(usuarioSupervisorDTO.getNombre());
        usuarioSupervisor.setCredenciales(usuarioSupervisorDTO.getCredenciales());
        usuarioSupervisor.setRol(usuarioSupervisorDTO.getRol());
        usuarioSupervisor.setNivelAcceso(usuarioSupervisorDTO.getNivelAcceso());
        return usuarioSupervisor;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final UsuarioSupervisor usuarioSupervisor = usuarioSupervisorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Evento rel8Evento = eventoRepository.findFirstByRel8(usuarioSupervisor);
        if (rel8Evento != null) {
            referencedWarning.setKey("usuarioSupervisor.evento.rel8.referenced");
            referencedWarning.addParam(rel8Evento.getId());
            return referencedWarning;
        }
        final SistemaSeguridad rel12SistemaSeguridad = sistemaSeguridadRepository.findFirstByRel12(usuarioSupervisor);
        if (rel12SistemaSeguridad != null) {
            referencedWarning.setKey("usuarioSupervisor.sistemaSeguridad.rel12.referenced");
            referencedWarning.addParam(rel12SistemaSeguridad.getId());
            return referencedWarning;
        }
        return null;
    }

}
