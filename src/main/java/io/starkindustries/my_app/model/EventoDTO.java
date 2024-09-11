package io.starkindustries.my_app.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EventoDTO {

    private Long id;

    @Size(max = 255)
    private String tipo;

    private LocalDate fecha;

    @Size(max = 255)
    private String gravedad;

    @Size(max = 255)
    private String sensorasociado;

    @Size(max = 255)
    private String respuesta;

    private Long rel5;

    private Long rel6;

    private Long rel7;

    private Long rel8;

    private Long rel11;

}
