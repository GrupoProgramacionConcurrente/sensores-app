package io.starkindustries.my_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "UsuarioSupervisors")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class UsuarioSupervisor {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @Column
    private String credenciales;

    @Column
    private String rol;

    @Column
    private String nivelAcceso;

    @OneToMany(mappedBy = "rel8")
    private Set<Evento> rel8;

    @ManyToMany(mappedBy = "rel10")
    private Set<Notificacion> rel10;

    @OneToMany(mappedBy = "rel12")
    private Set<SistemaSeguridad> rel12;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
