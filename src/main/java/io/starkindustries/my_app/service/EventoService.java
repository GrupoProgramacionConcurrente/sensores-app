package io.starkindustries.my_app.service;

import io.starkindustries.my_app.domain.Evento;
import io.starkindustries.my_app.domain.Respuesta;
import io.starkindustries.my_app.domain.SensorAcceso;
import io.starkindustries.my_app.domain.SensorMovimiento;
import io.starkindustries.my_app.domain.SensorTemperatura;
import io.starkindustries.my_app.domain.UsuarioSupervisor;
import io.starkindustries.my_app.model.EventoDTO;
import io.starkindustries.my_app.repos.EventoRepository;
import io.starkindustries.my_app.repos.RespuestaRepository;
import io.starkindustries.my_app.repos.SensorAccesoRepository;
import io.starkindustries.my_app.repos.SensorMovimientoRepository;
import io.starkindustries.my_app.repos.SensorTemperaturaRepository;
import io.starkindustries.my_app.repos.UsuarioSupervisorRepository;
import io.starkindustries.my_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final SensorMovimientoRepository sensorMovimientoRepository;
    private final SensorTemperaturaRepository sensorTemperaturaRepository;
    private final SensorAccesoRepository sensorAccesoRepository;
    private final UsuarioSupervisorRepository usuarioSupervisorRepository;
    private final RespuestaRepository respuestaRepository;

    public EventoService(final EventoRepository eventoRepository,
            final SensorMovimientoRepository sensorMovimientoRepository,
            final SensorTemperaturaRepository sensorTemperaturaRepository,
            final SensorAccesoRepository sensorAccesoRepository,
            final UsuarioSupervisorRepository usuarioSupervisorRepository,
            final RespuestaRepository respuestaRepository) {
        this.eventoRepository = eventoRepository;
        this.sensorMovimientoRepository = sensorMovimientoRepository;
        this.sensorTemperaturaRepository = sensorTemperaturaRepository;
        this.sensorAccesoRepository = sensorAccesoRepository;
        this.usuarioSupervisorRepository = usuarioSupervisorRepository;
        this.respuestaRepository = respuestaRepository;
    }

    public List<EventoDTO> findAll() {
        final List<Evento> eventoes = eventoRepository.findAll(Sort.by("id"));
        return eventoes.stream()
                .map(evento -> mapToDTO(evento, new EventoDTO()))
                .toList();
    }

    public EventoDTO get(final Long id) {
        return eventoRepository.findById(id)
                .map(evento -> mapToDTO(evento, new EventoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EventoDTO eventoDTO) {
        final Evento evento = new Evento();
        mapToEntity(eventoDTO, evento);
        return eventoRepository.save(evento).getId();
    }

    public void update(final Long id, final EventoDTO eventoDTO) {
        final Evento evento = eventoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(eventoDTO, evento);
        eventoRepository.save(evento);
    }

    public void delete(final Long id) {
        eventoRepository.deleteById(id);
    }

    private EventoDTO mapToDTO(final Evento evento, final EventoDTO eventoDTO) {
        eventoDTO.setId(evento.getId());
        eventoDTO.setTipo(evento.getTipo());
        eventoDTO.setFecha(evento.getFecha());
        eventoDTO.setGravedad(evento.getGravedad());
        eventoDTO.setSensorasociado(evento.getSensorasociado());
        eventoDTO.setRespuesta(evento.getRespuesta());
        eventoDTO.setRel5(evento.getRel5() == null ? null : evento.getRel5().getId());
        eventoDTO.setRel6(evento.getRel6() == null ? null : evento.getRel6().getId());
        eventoDTO.setRel7(evento.getRel7() == null ? null : evento.getRel7().getId());
        eventoDTO.setRel8(evento.getRel8() == null ? null : evento.getRel8().getId());
        eventoDTO.setRel11(evento.getRel11() == null ? null : evento.getRel11().getId());
        return eventoDTO;
    }

    private Evento mapToEntity(final EventoDTO eventoDTO, final Evento evento) {
        evento.setTipo(eventoDTO.getTipo());
        evento.setFecha(eventoDTO.getFecha());
        evento.setGravedad(eventoDTO.getGravedad());
        evento.setSensorasociado(eventoDTO.getSensorasociado());
        evento.setRespuesta(eventoDTO.getRespuesta());
        final SensorMovimiento rel5 = eventoDTO.getRel5() == null ? null : sensorMovimientoRepository.findById(eventoDTO.getRel5())
                .orElseThrow(() -> new NotFoundException("rel5 not found"));
        evento.setRel5(rel5);
        final SensorTemperatura rel6 = eventoDTO.getRel6() == null ? null : sensorTemperaturaRepository.findById(eventoDTO.getRel6())
                .orElseThrow(() -> new NotFoundException("rel6 not found"));
        evento.setRel6(rel6);
        final SensorAcceso rel7 = eventoDTO.getRel7() == null ? null : sensorAccesoRepository.findById(eventoDTO.getRel7())
                .orElseThrow(() -> new NotFoundException("rel7 not found"));
        evento.setRel7(rel7);
        final UsuarioSupervisor rel8 = eventoDTO.getRel8() == null ? null : usuarioSupervisorRepository.findById(eventoDTO.getRel8())
                .orElseThrow(() -> new NotFoundException("rel8 not found"));
        evento.setRel8(rel8);
        final Respuesta rel11 = eventoDTO.getRel11() == null ? null : respuestaRepository.findById(eventoDTO.getRel11())
                .orElseThrow(() -> new NotFoundException("rel11 not found"));
        evento.setRel11(rel11);
        return evento;
    }

}
