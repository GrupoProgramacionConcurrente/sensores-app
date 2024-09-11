package io.starkindustries.my_app.repos;

import io.starkindustries.my_app.domain.SensorMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SensorMovimientoRepository extends JpaRepository<SensorMovimiento, Long> {
}
