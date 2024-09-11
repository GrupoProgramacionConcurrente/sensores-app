package io.starkindustries.my_app.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SedeDTO {

    private Long id;

    @Size(max = 255)
    private String ubicacion;

    @Size(max = 255)
    private String nombre;

    @SedeRel1Unique
    private Long rel1;

}
