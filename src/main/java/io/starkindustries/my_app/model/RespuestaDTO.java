package io.starkindustries.my_app.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RespuestaDTO {

    private Long id;

    @Size(max = 255)
    private String tipo;

    @Size(max = 255)
    private String tiempoRespuesta;

    @Size(max = 255)
    private String accionesTomadas;

}
