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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "SistemaSeguridads")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class SistemaSeguridad {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @Column
    private Double version;

    @Column
    private LocalDate fechainstalacion;

    @Column
    private String estado;

    @OneToOne(mappedBy = "rel1", fetch = FetchType.LAZY)
    private Sede rel1;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rel2id", unique = true)
    private SensorMovimiento rel2;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rel3id", unique = true)
    private SensorTemperatura rel3;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rel4id", unique = true)
    private SensorAcceso rel4;

    @OneToMany(mappedBy = "rel9")
    private Set<Notificacion> rel9;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rel12id")
    private UsuarioSupervisor rel12;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
