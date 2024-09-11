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
@Table(name = "SensorMovimientoes")
@Getter
@Setter
public class SensorMovimiento extends Sensor {

    @OneToOne(mappedBy = "rel2", fetch = FetchType.LAZY)
    private SistemaSeguridad rel2;

    @OneToMany(mappedBy = "rel5")
    private Set<Evento> rel5;

}
