package io.starkindustries.my_app.repos;

import io.starkindustries.my_app.domain.Notificacion;
import io.starkindustries.my_app.domain.SistemaSeguridad;
import io.starkindustries.my_app.domain.UsuarioSupervisor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    Notificacion findFirstByRel9(SistemaSeguridad sistemaSeguridad);

    Notificacion findFirstByRel10(UsuarioSupervisor usuarioSupervisor);

    List<Notificacion> findAllByRel10(UsuarioSupervisor usuarioSupervisor);

}
