package io.starkindustries.my_app.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NotificacionDTO {

    private Long id;

    @Size(max = 255)
    private String mensaje;

    private LocalDateTime fechaHoraEnvio;

    @Size(max = 255)
    private String destinatario;

    @Size(max = 255)
    private String tipoNotificacion;

    private Long rel9;

    private List<Long> rel10;

}
