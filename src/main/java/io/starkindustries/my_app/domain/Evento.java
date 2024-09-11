package io.starkindustries.my_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "Eventoes")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Evento {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String tipo;

    @Column
    private LocalDate fecha;

    @Column
    private String gravedad;

    @Column
    private String sensorasociado;

    @Column
    private String respuesta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rel5id")
    private SensorMovimiento rel5;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rel6id")
    private SensorTemperatura rel6;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rel7id")
    private SensorAcceso rel7;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rel8id")
    private UsuarioSupervisor rel8;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rel11id")
    private Respuesta rel11;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
