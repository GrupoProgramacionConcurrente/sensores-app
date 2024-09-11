package io.starkindustries.my_app.service;

import io.starkindustries.my_app.domain.Sede;
import io.starkindustries.my_app.domain.SistemaSeguridad;
import io.starkindustries.my_app.model.SedeDTO;
import io.starkindustries.my_app.repos.SedeRepository;
import io.starkindustries.my_app.repos.SistemaSeguridadRepository;
import io.starkindustries.my_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SedeService {

    private final SedeRepository sedeRepository;
    private final SistemaSeguridadRepository sistemaSeguridadRepository;

    public SedeService(final SedeRepository sedeRepository,
            final SistemaSeguridadRepository sistemaSeguridadRepository) {
        this.sedeRepository = sedeRepository;
        this.sistemaSeguridadRepository = sistemaSeguridadRepository;
    }

    public List<SedeDTO> findAll() {
        final List<Sede> sedes = sedeRepository.findAll(Sort.by("id"));
        return sedes.stream()
                .map(sede -> mapToDTO(sede, new SedeDTO()))
                .toList();
    }

    public SedeDTO get(final Long id) {
        return sedeRepository.findById(id)
                .map(sede -> mapToDTO(sede, new SedeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SedeDTO sedeDTO) {
        final Sede sede = new Sede();
        mapToEntity(sedeDTO, sede);
        return sedeRepository.save(sede).getId();
    }

    public void update(final Long id, final SedeDTO sedeDTO) {
        final Sede sede = sedeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(sedeDTO, sede);
        sedeRepository.save(sede);
    }

    public void delete(final Long id) {
        sedeRepository.deleteById(id);
    }

    private SedeDTO mapToDTO(final Sede sede, final SedeDTO sedeDTO) {
        sedeDTO.setId(sede.getId());
        sedeDTO.setUbicacion(sede.getUbicacion());
        sedeDTO.setNombre(sede.getNombre());
        sedeDTO.setRel1(sede.getRel1() == null ? null : sede.getRel1().getId());
        return sedeDTO;
    }

    private Sede mapToEntity(final SedeDTO sedeDTO, final Sede sede) {
        sede.setUbicacion(sedeDTO.getUbicacion());
        sede.setNombre(sedeDTO.getNombre());
        final SistemaSeguridad rel1 = sedeDTO.getRel1() == null ? null : sistemaSeguridadRepository.findById(sedeDTO.getRel1())
                .orElseThrow(() -> new NotFoundException("rel1 not found"));
        sede.setRel1(rel1);
        return sede;
    }

    public boolean rel1Exists(final Long id) {
        return sedeRepository.existsByRel1Id(id);
    }

}
