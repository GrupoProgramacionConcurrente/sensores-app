package io.starkindustries.my_app.repos;

import io.starkindustries.my_app.domain.SensorAcceso;
import io.starkindustries.my_app.domain.SensorMovimiento;
import io.starkindustries.my_app.domain.SensorTemperatura;
import io.starkindustries.my_app.domain.SistemaSeguridad;
import io.starkindustries.my_app.domain.UsuarioSupervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SistemaSeguridadRepository extends JpaRepository<SistemaSeguridad, Long> {

    SistemaSeguridad findFirstByRel2(SensorMovimiento sensorMovimiento);

    SistemaSeguridad findFirstByRel3(SensorTemperatura sensorTemperatura);

    SistemaSeguridad findFirstByRel4(SensorAcceso sensorAcceso);

    SistemaSeguridad findFirstByRel12(UsuarioSupervisor usuarioSupervisor);

    boolean existsByRel2Id(Long id);

    boolean existsByRel3Id(Long id);

    boolean existsByRel4Id(Long id);

}