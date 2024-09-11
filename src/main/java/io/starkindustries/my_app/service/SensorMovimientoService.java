package io.starkindustries.my_app.service;

import io.starkindustries.my_app.domain.Evento;
import io.starkindustries.my_app.domain.SensorMovimiento;
import io.starkindustries.my_app.domain.SistemaSeguridad;
import io.starkindustries.my_app.model.SensorMovimientoDTO;
import io.starkindustries.my_app.repos.EventoRepository;
import io.starkindustries.my_app.repos.SensorMovimientoRepository;
import io.starkindustries.my_app.repos.SistemaSeguridadRepository;
import io.starkindustries.my_app.util.NotFoundException;
import io.starkindustries.my_app.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SensorMovimientoService {

    private final SensorMovimientoRepository sensorMovimientoRepository;
    private final SistemaSeguridadRepository sistemaSeguridadRepository;
    private final EventoRepository eventoRepository;

    public SensorMovimientoService(final SensorMovimientoRepository sensorMovimientoRepository,
            final SistemaSeguridadRepository sistemaSeguridadRepository,
            final EventoRepository eventoRepository) {
        this.sensorMovimientoRepository = sensorMovimientoRepository;
        this.sistemaSeguridadRepository = sistemaSeguridadRepository;
        this.eventoRepository = eventoRepository;
    }

    public List<SensorMovimientoDTO> findAll() {
        final List<SensorMovimiento> sensorMovimientoes = sensorMovimientoRepository.findAll(Sort.by("id"));
        return sensorMovimientoes.stream()
                .map(sensorMovimiento -> mapToDTO(sensorMovimiento, new SensorMovimientoDTO()))
                .toList();
    }

    public SensorMovimientoDTO get(final Long id) {
        return sensorMovimientoRepository.findById(id)
                .map(sensorMovimiento -> mapToDTO(sensorMovimiento, new SensorMovimientoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SensorMovimientoDTO sensorMovimientoDTO) {
        final SensorMovimiento sensorMovimiento = new SensorMovimiento();
        mapToEntity(sensorMovimientoDTO, sensorMovimiento);
        return sensorMovimientoRepository.save(sensorMovimiento).getId();
    }

    public void update(final Long id, final SensorMovimientoDTO sensorMovimientoDTO) {
        final SensorMovimiento sensorMovimiento = sensorMovimientoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(sensorMovimientoDTO, sensorMovimiento);
        sensorMovimientoRepository.save(sensorMovimiento);
    }

    public void delete(final Long id) {
        sensorMovimientoRepository.deleteById(id);
    }

    private SensorMovimientoDTO mapToDTO(final SensorMovimiento sensorMovimiento,
            final SensorMovimientoDTO sensorMovimientoDTO) {
        sensorMovimientoDTO.setId(sensorMovimiento.getId());
        sensorMovimientoDTO.setNombre(sensorMovimiento.getNombre());
        sensorMovimientoDTO.setEstado(sensorMovimiento.getEstado());
        return sensorMovimientoDTO;
    }

    private SensorMovimiento mapToEntity(final SensorMovimientoDTO sensorMovimientoDTO,
            final SensorMovimiento sensorMovimiento) {
        sensorMovimiento.setNombre(sensorMovimientoDTO.getNombre());
        sensorMovimiento.setEstado(sensorMovimientoDTO.getEstado());
        return sensorMovimiento;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final SensorMovimiento sensorMovimiento = sensorMovimientoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final SistemaSeguridad rel2SistemaSeguridad = sistemaSeguridadRepository.findFirstByRel2(sensorMovimiento);
        if (rel2SistemaSeguridad != null) {
            referencedWarning.setKey("sensorMovimiento.sistemaSeguridad.rel2.referenced");
            referencedWarning.addParam(rel2SistemaSeguridad.getId());
            return referencedWarning;
        }
        final Evento rel5Evento = eventoRepository.findFirstByRel5(sensorMovimiento);
        if (rel5Evento != null) {
            referencedWarning.setKey("sensorMovimiento.evento.rel5.referenced");
            referencedWarning.addParam(rel5Evento.getId());
            return referencedWarning;
        }
        return null;
    }

}
