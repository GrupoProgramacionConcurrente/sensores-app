package io.starkindustries.my_app.service;

import io.starkindustries.my_app.domain.Evento;
import io.starkindustries.my_app.domain.Respuesta;
import io.starkindustries.my_app.model.RespuestaDTO;
import io.starkindustries.my_app.repos.EventoRepository;
import io.starkindustries.my_app.repos.RespuestaRepository;
import io.starkindustries.my_app.util.NotFoundException;
import io.starkindustries.my_app.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class RespuestaService {

    private final RespuestaRepository respuestaRepository;
    private final EventoRepository eventoRepository;

    public RespuestaService(final RespuestaRepository respuestaRepository,
            final EventoRepository eventoRepository) {
        this.respuestaRepository = respuestaRepository;
        this.eventoRepository = eventoRepository;
    }

    public List<RespuestaDTO> findAll() {
        final List<Respuesta> respuestas = respuestaRepository.findAll(Sort.by("id"));
        return respuestas.stream()
                .map(respuesta -> mapToDTO(respuesta, new RespuestaDTO()))
                .toList();
    }

    public RespuestaDTO get(final Long id) {
        return respuestaRepository.findById(id)
                .map(respuesta -> mapToDTO(respuesta, new RespuestaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final RespuestaDTO respuestaDTO) {
        final Respuesta respuesta = new Respuesta();
        mapToEntity(respuestaDTO, respuesta);
        return respuestaRepository.save(respuesta).getId();
    }

    public void update(final Long id, final RespuestaDTO respuestaDTO) {
        final Respuesta respuesta = respuestaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(respuestaDTO, respuesta);
        respuestaRepository.save(respuesta);
    }

    public void delete(final Long id) {
        respuestaRepository.deleteById(id);
    }

    private RespuestaDTO mapToDTO(final Respuesta respuesta, final RespuestaDTO respuestaDTO) {
        respuestaDTO.setId(respuesta.getId());
        respuestaDTO.setTipo(respuesta.getTipo());
        respuestaDTO.setTiempoRespuesta(respuesta.getTiempoRespuesta());
        respuestaDTO.setAccionesTomadas(respuesta.getAccionesTomadas());
        return respuestaDTO;
    }

    private Respuesta mapToEntity(final RespuestaDTO respuestaDTO, final Respuesta respuesta) {
        respuesta.setTipo(respuestaDTO.getTipo());
        respuesta.setTiempoRespuesta(respuestaDTO.getTiempoRespuesta());
        respuesta.setAccionesTomadas(respuestaDTO.getAccionesTomadas());
        return respuesta;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Respuesta respuesta = respuestaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Evento rel11Evento = eventoRepository.findFirstByRel11(respuesta);
        if (rel11Evento != null) {
            referencedWarning.setKey("respuesta.evento.rel11.referenced");
            referencedWarning.addParam(rel11Evento.getId());
            return referencedWarning;
        }
        return null;
    }

}
