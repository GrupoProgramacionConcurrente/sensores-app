package io.starkindustries.my_app.service;

import io.starkindustries.my_app.domain.Evento;
import io.starkindustries.my_app.domain.SensorAcceso;
import io.starkindustries.my_app.domain.SistemaSeguridad;
import io.starkindustries.my_app.model.SensorAccesoDTO;
import io.starkindustries.my_app.repos.EventoRepository;
import io.starkindustries.my_app.repos.SensorAccesoRepository;
import io.starkindustries.my_app.repos.SistemaSeguridadRepository;
import io.starkindustries.my_app.util.NotFoundException;
import io.starkindustries.my_app.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SensorAccesoService {

    private final SensorAccesoRepository sensorAccesoRepository;
    private final SistemaSeguridadRepository sistemaSeguridadRepository;
    private final EventoRepository eventoRepository;

    public SensorAccesoService(final SensorAccesoRepository sensorAccesoRepository,
            final SistemaSeguridadRepository sistemaSeguridadRepository,
            final EventoRepository eventoRepository) {
        this.sensorAccesoRepository = sensorAccesoRepository;
        this.sistemaSeguridadRepository = sistemaSeguridadRepository;
        this.eventoRepository = eventoRepository;
    }

    public List<SensorAccesoDTO> findAll() {
        final List<SensorAcceso> sensorAccesoes = sensorAccesoRepository.findAll(Sort.by("id"));
        return sensorAccesoes.stream()
                .map(sensorAcceso -> mapToDTO(sensorAcceso, new SensorAccesoDTO()))
                .toList();
    }

    public SensorAccesoDTO get(final Long id) {
        return sensorAccesoRepository.findById(id)
                .map(sensorAcceso -> mapToDTO(sensorAcceso, new SensorAccesoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SensorAccesoDTO sensorAccesoDTO) {
        final SensorAcceso sensorAcceso = new SensorAcceso();
        mapToEntity(sensorAccesoDTO, sensorAcceso);
        return sensorAccesoRepository.save(sensorAcceso).getId();
    }

    public void update(final Long id, final SensorAccesoDTO sensorAccesoDTO) {
        final SensorAcceso sensorAcceso = sensorAccesoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(sensorAccesoDTO, sensorAcceso);
        sensorAccesoRepository.save(sensorAcceso);
    }

    public void delete(final Long id) {
        sensorAccesoRepository.deleteById(id);
    }

    private SensorAccesoDTO mapToDTO(final SensorAcceso sensorAcceso,
            final SensorAccesoDTO sensorAccesoDTO) {
        sensorAccesoDTO.setId(sensorAcceso.getId());
        sensorAccesoDTO.setNombre(sensorAcceso.getNombre());
        sensorAccesoDTO.setEstado(sensorAcceso.getEstado());
        return sensorAccesoDTO;
    }

    private SensorAcceso mapToEntity(final SensorAccesoDTO sensorAccesoDTO,
            final SensorAcceso sensorAcceso) {
        sensorAcceso.setNombre(sensorAccesoDTO.getNombre());
        sensorAcceso.setEstado(sensorAccesoDTO.getEstado());
        return sensorAcceso;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final SensorAcceso sensorAcceso = sensorAccesoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final SistemaSeguridad rel4SistemaSeguridad = sistemaSeguridadRepository.findFirstByRel4(sensorAcceso);
        if (rel4SistemaSeguridad != null) {
            referencedWarning.setKey("sensorAcceso.sistemaSeguridad.rel4.referenced");
            referencedWarning.addParam(rel4SistemaSeguridad.getId());
            return referencedWarning;
        }
        final Evento rel7Evento = eventoRepository.findFirstByRel7(sensorAcceso);
        if (rel7Evento != null) {
            referencedWarning.setKey("sensorAcceso.evento.rel7.referenced");
            referencedWarning.addParam(rel7Evento.getId());
            return referencedWarning;
        }
        return null;
    }

}
