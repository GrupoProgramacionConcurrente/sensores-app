package io.starkindustries.my_app.service;

import io.starkindustries.my_app.domain.Notificacion;
import io.starkindustries.my_app.domain.Sede;
import io.starkindustries.my_app.domain.SensorAcceso;
import io.starkindustries.my_app.domain.SensorMovimiento;
import io.starkindustries.my_app.domain.SensorTemperatura;
import io.starkindustries.my_app.domain.SistemaSeguridad;
import io.starkindustries.my_app.domain.UsuarioSupervisor;
import io.starkindustries.my_app.model.SistemaSeguridadDTO;
import io.starkindustries.my_app.repos.NotificacionRepository;
import io.starkindustries.my_app.repos.SedeRepository;
import io.starkindustries.my_app.repos.SensorAccesoRepository;
import io.starkindustries.my_app.repos.SensorMovimientoRepository;
import io.starkindustries.my_app.repos.SensorTemperaturaRepository;
import io.starkindustries.my_app.repos.SistemaSeguridadRepository;
import io.starkindustries.my_app.repos.UsuarioSupervisorRepository;
import io.starkindustries.my_app.util.NotFoundException;
import io.starkindustries.my_app.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SistemaSeguridadService {

    private final SistemaSeguridadRepository sistemaSeguridadRepository;
    private final SensorMovimientoRepository sensorMovimientoRepository;
    private final SensorTemperaturaRepository sensorTemperaturaRepository;
    private final SensorAccesoRepository sensorAccesoRepository;
    private final UsuarioSupervisorRepository usuarioSupervisorRepository;
    private final SedeRepository sedeRepository;
    private final NotificacionRepository notificacionRepository;

    public SistemaSeguridadService(final SistemaSeguridadRepository sistemaSeguridadRepository,
            final SensorMovimientoRepository sensorMovimientoRepository,
            final SensorTemperaturaRepository sensorTemperaturaRepository,
            final SensorAccesoRepository sensorAccesoRepository,
            final UsuarioSupervisorRepository usuarioSupervisorRepository,
            final SedeRepository sedeRepository,
            final NotificacionRepository notificacionRepository) {
        this.sistemaSeguridadRepository = sistemaSeguridadRepository;
        this.sensorMovimientoRepository = sensorMovimientoRepository;
        this.sensorTemperaturaRepository = sensorTemperaturaRepository;
        this.sensorAccesoRepository = sensorAccesoRepository;
        this.usuarioSupervisorRepository = usuarioSupervisorRepository;
        this.sedeRepository = sedeRepository;
        this.notificacionRepository = notificacionRepository;
    }

    public List<SistemaSeguridadDTO> findAll() {
        final List<SistemaSeguridad> sistemaSeguridads = sistemaSeguridadRepository.findAll(Sort.by("id"));
        return sistemaSeguridads.stream()
                .map(sistemaSeguridad -> mapToDTO(sistemaSeguridad, new SistemaSeguridadDTO()))
                .toList();
    }

    public SistemaSeguridadDTO get(final Long id) {
        return sistemaSeguridadRepository.findById(id)
                .map(sistemaSeguridad -> mapToDTO(sistemaSeguridad, new SistemaSeguridadDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SistemaSeguridadDTO sistemaSeguridadDTO) {
        final SistemaSeguridad sistemaSeguridad = new SistemaSeguridad();
        mapToEntity(sistemaSeguridadDTO, sistemaSeguridad);
        return sistemaSeguridadRepository.save(sistemaSeguridad).getId();
    }

    public void update(final Long id, final SistemaSeguridadDTO sistemaSeguridadDTO) {
        final SistemaSeguridad sistemaSeguridad = sistemaSeguridadRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(sistemaSeguridadDTO, sistemaSeguridad);
        sistemaSeguridadRepository.save(sistemaSeguridad);
    }

    public void delete(final Long id) {
        sistemaSeguridadRepository.deleteById(id);
    }

    private SistemaSeguridadDTO mapToDTO(final SistemaSeguridad sistemaSeguridad,
            final SistemaSeguridadDTO sistemaSeguridadDTO) {
        sistemaSeguridadDTO.setId(sistemaSeguridad.getId());
        sistemaSeguridadDTO.setNombre(sistemaSeguridad.getNombre());
        sistemaSeguridadDTO.setVersion(sistemaSeguridad.getVersion());
        sistemaSeguridadDTO.setFechainstalacion(sistemaSeguridad.getFechainstalacion());
        sistemaSeguridadDTO.setEstado(sistemaSeguridad.getEstado());
        sistemaSeguridadDTO.setRel2(sistemaSeguridad.getRel2() == null ? null : sistemaSeguridad.getRel2().getId());
        sistemaSeguridadDTO.setRel3(sistemaSeguridad.getRel3() == null ? null : sistemaSeguridad.getRel3().getId());
        sistemaSeguridadDTO.setRel4(sistemaSeguridad.getRel4() == null ? null : sistemaSeguridad.getRel4().getId());
        sistemaSeguridadDTO.setRel12(sistemaSeguridad.getRel12() == null ? null : sistemaSeguridad.getRel12().getId());
        return sistemaSeguridadDTO;
    }

    private SistemaSeguridad mapToEntity(final SistemaSeguridadDTO sistemaSeguridadDTO,
            final SistemaSeguridad sistemaSeguridad) {
        sistemaSeguridad.setNombre(sistemaSeguridadDTO.getNombre());
        sistemaSeguridad.setVersion(sistemaSeguridadDTO.getVersion());
        sistemaSeguridad.setFechainstalacion(sistemaSeguridadDTO.getFechainstalacion());
        sistemaSeguridad.setEstado(sistemaSeguridadDTO.getEstado());
        final SensorMovimiento rel2 = sistemaSeguridadDTO.getRel2() == null ? null : sensorMovimientoRepository.findById(sistemaSeguridadDTO.getRel2())
                .orElseThrow(() -> new NotFoundException("rel2 not found"));
        sistemaSeguridad.setRel2(rel2);
        final SensorTemperatura rel3 = sistemaSeguridadDTO.getRel3() == null ? null : sensorTemperaturaRepository.findById(sistemaSeguridadDTO.getRel3())
                .orElseThrow(() -> new NotFoundException("rel3 not found"));
        sistemaSeguridad.setRel3(rel3);
        final SensorAcceso rel4 = sistemaSeguridadDTO.getRel4() == null ? null : sensorAccesoRepository.findById(sistemaSeguridadDTO.getRel4())
                .orElseThrow(() -> new NotFoundException("rel4 not found"));
        sistemaSeguridad.setRel4(rel4);
        final UsuarioSupervisor rel12 = sistemaSeguridadDTO.getRel12() == null ? null : usuarioSupervisorRepository.findById(sistemaSeguridadDTO.getRel12())
                .orElseThrow(() -> new NotFoundException("rel12 not found"));
        sistemaSeguridad.setRel12(rel12);
        return sistemaSeguridad;
    }

    public boolean rel2Exists(final Long id) {
        return sistemaSeguridadRepository.existsByRel2Id(id);
    }

    public boolean rel3Exists(final Long id) {
        return sistemaSeguridadRepository.existsByRel3Id(id);
    }

    public boolean rel4Exists(final Long id) {
        return sistemaSeguridadRepository.existsByRel4Id(id);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final SistemaSeguridad sistemaSeguridad = sistemaSeguridadRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Sede rel1Sede = sedeRepository.findFirstByRel1(sistemaSeguridad);
        if (rel1Sede != null) {
            referencedWarning.setKey("sistemaSeguridad.sede.rel1.referenced");
            referencedWarning.addParam(rel1Sede.getId());
            return referencedWarning;
        }
        final Notificacion rel9Notificacion = notificacionRepository.findFirstByRel9(sistemaSeguridad);
        if (rel9Notificacion != null) {
            referencedWarning.setKey("sistemaSeguridad.notificacion.rel9.referenced");
            referencedWarning.addParam(rel9Notificacion.getId());
            return referencedWarning;
        }
        return null;
    }

}
