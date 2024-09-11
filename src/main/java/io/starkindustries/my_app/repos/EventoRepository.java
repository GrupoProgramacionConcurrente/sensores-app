package io.starkindustries.my_app.repos;

import io.starkindustries.my_app.domain.Evento;
import io.starkindustries.my_app.domain.Respuesta;
import io.starkindustries.my_app.domain.SensorAcceso;
import io.starkindustries.my_app.domain.SensorMovimiento;
import io.starkindustries.my_app.domain.SensorTemperatura;
import io.starkindustries.my_app.domain.UsuarioSupervisor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventoRepository extends JpaRepository<Evento, Long> {

    Evento findFirstByRel5(SensorMovimiento sensorMovimiento);

    Evento findFirstByRel6(SensorTemperatura sensorTemperatura);

    Evento findFirstByRel7(SensorAcceso sensorAcceso);

    Evento findFirstByRel8(UsuarioSupervisor usuarioSupervisor);

    Evento findFirstByRel11(Respuesta respuesta);

}
