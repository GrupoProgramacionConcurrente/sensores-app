package io.starkindustries.my_app.repos;

import io.starkindustries.my_app.domain.Sede;
import io.starkindustries.my_app.domain.SistemaSeguridad;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SedeRepository extends JpaRepository<Sede, Long> {

    Sede findFirstByRel1(SistemaSeguridad sistemaSeguridad);

    boolean existsByRel1Id(Long id);

}
