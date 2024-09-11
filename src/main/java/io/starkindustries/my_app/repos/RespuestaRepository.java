package io.starkindustries.my_app.repos;

import io.starkindustries.my_app.domain.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
}
