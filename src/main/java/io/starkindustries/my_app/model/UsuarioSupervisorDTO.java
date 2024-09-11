package io.starkindustries.my_app.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsuarioSupervisorDTO {

    private Long id;

    @Size(max = 255)
    private String nombre;

    @Size(max = 255)
    private String credenciales;

    @Size(max = 255)
    private String rol;

    @Size(max = 255)
    private String nivelAcceso;

}
