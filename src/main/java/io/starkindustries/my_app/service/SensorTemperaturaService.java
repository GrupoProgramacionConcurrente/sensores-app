package io.starkindustries.my_app.service;

import io.starkindustries.my_app.domain.Evento;
import io.starkindustries.my_app.domain.SensorTemperatura;
import io.starkindustries.my_app.domain.SistemaSeguridad;
import io.starkindustries.my_app.model.SensorTemperaturaDTO;
import io.starkindustries.my_app.repos.EventoRepository;
import io.starkindustries.my_app.repos.SensorTemperaturaRepository;
import io.starkindustries.my_app.repos.SistemaSeguridadRepository;
import io.starkindustries.my_app.util.NotFoundException;
import io.starkindustries.my_app.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SensorTemperaturaService {

    private final SensorTemperaturaRepository sensorTemperaturaRepository;
    private final SistemaSeguridadRepository sistemaSeguridadRepository;
    private final EventoRepository eventoRepository;

    public SensorTemperaturaService(final SensorTemperaturaRepository sensorTemperaturaRepository,
            final SistemaSeguridadRepository sistemaSeguridadRepository,
            final EventoRepository eventoRepository) {
        this.sensorTemperaturaRepository = sensorTemperaturaRepository;
        this.sistemaSeguridadRepository = sistemaSeguridadRepository;
        this.eventoRepository = eventoRepository;
    }

    public List<SensorTemperaturaDTO> findAll() {
        final List<SensorTemperatura> sensorTemperaturas = sensorTemperaturaRepository.findAll(Sort.by("id"));
        return sensorTemperaturas.stream()
                .map(sensorTemperatura -> mapToDTO(sensorTemperatura, new SensorTemperaturaDTO()))
                .toList();
    }

    public SensorTemperaturaDTO get(final Long id) {
        return sensorTemperaturaRepository.findById(id)
                .map(sensorTemperatura -> mapToDTO(sensorTemperatura, new SensorTemperaturaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SensorTemperaturaDTO sensorTemperaturaDTO) {
        final SensorTemperatura sensorTemperatura = new SensorTemperatura();
        mapToEntity(sensorTemperaturaDTO, sensorTemperatura);
        return sensorTemperaturaRepository.save(sensorTemperatura).getId();
    }

    public void update(final Long id, final SensorTemperaturaDTO sensorTemperaturaDTO) {
        final SensorTemperatura sensorTemperatura = sensorTemperaturaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(sensorTemperaturaDTO, sensorTemperatura);
        sensorTemperaturaRepository.save(sensorTemperatura);
    }

    public void delete(final Long id) {
        sensorTemperaturaRepository.deleteById(id);
    }

    private SensorTemperaturaDTO mapToDTO(final SensorTemperatura sensorTemperatura,
            final SensorTemperaturaDTO sensorTemperaturaDTO) {
        sensorTemperaturaDTO.setId(sensorTemperatura.getId());
        sensorTemperaturaDTO.setNombre(sensorTemperatura.getNombre());
        sensorTemperaturaDTO.setEstado(sensorTemperatura.getEstado());
        return sensorTemperaturaDTO;
    }

    private SensorTemperatura mapToEntity(final SensorTemperaturaDTO sensorTemperaturaDTO,
            final SensorTemperatura sensorTemperatura) {
        sensorTemperatura.setNombre(sensorTemperaturaDTO.getNombre());
        sensorTemperatura.setEstado(sensorTemperaturaDTO.getEstado());
        return sensorTemperatura;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final SensorTemperatura sensorTemperatura = sensorTemperaturaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final SistemaSeguridad rel3SistemaSeguridad = sistemaSeguridadRepository.findFirstByRel3(sensorTemperatura);
        if (rel3SistemaSeguridad != null) {
            referencedWarning.setKey("sensorTemperatura.sistemaSeguridad.rel3.referenced");
            referencedWarning.addParam(rel3SistemaSeguridad.getId());
            return referencedWarning;
        }
        final Evento rel6Evento = eventoRepository.findFirstByRel6(sensorTemperatura);
        if (rel6Evento != null) {
            referencedWarning.setKey("sensorTemperatura.evento.rel6.referenced");
            referencedWarning.addParam(rel6Evento.getId());
            return referencedWarning;
        }
        return null;
    }

}
