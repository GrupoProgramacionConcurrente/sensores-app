package io.starkindustries.my_app.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "SensorTemperaturas")
@Getter
@Setter
public class SensorTemperatura extends Sensor {

    @OneToOne(mappedBy = "rel3", fetch = FetchType.LAZY)
    private SistemaSeguridad rel3;

    @OneToMany(mappedBy = "rel6")
    private Set<Evento> rel6;

}
