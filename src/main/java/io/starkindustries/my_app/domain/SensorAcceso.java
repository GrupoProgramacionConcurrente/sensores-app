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
@Table(name = "SensorAccesoes")
@Getter
@Setter
public class SensorAcceso extends Sensor {

    @OneToOne(mappedBy = "rel4", fetch = FetchType.LAZY)
    private SistemaSeguridad rel4;

    @OneToMany(mappedBy = "rel7")
    private Set<Evento> rel7;

}
