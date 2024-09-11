package io.starkindustries.my_app.repos;

import io.starkindustries.my_app.domain.SensorTemperatura;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SensorTemperaturaRepository extends JpaRepository<SensorTemperatura, Long> {
}
