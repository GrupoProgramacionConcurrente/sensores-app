package io.starkindustries.my_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "Notificacions")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Notificacion {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String mensaje;

    @Column
    private LocalDateTime fechaHoraEnvio;

    @Column
    private String destinatario;

    @Column
    private String tipoNotificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rel9id")
    private SistemaSeguridad rel9;

    @ManyToMany
    @JoinTable(
            name = "NotificacionUsuarios",
            joinColumns = @JoinColumn(name = "notificacionId"),
            inverseJoinColumns = @JoinColumn(name = "usuarioSupervisorId")
    )
    private Set<UsuarioSupervisor> rel10;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
