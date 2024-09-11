package io.starkindustries.my_app.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Usuario {

    @Size(max = 255)
    private String nombre;

    @Size(max = 255)
    private String apellidos;

}
