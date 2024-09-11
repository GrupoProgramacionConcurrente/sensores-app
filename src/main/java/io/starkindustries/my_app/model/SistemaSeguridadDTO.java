package io.starkindustries.my_app.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SistemaSeguridadDTO {

    private Long id;

    @Size(max = 255)
    private String nombre;

    private Double version;

    private LocalDate fechainstalacion;

    @Size(max = 255)
    private String estado;

    @SistemaSeguridadRel2Unique
    private Long rel2;

    @SistemaSeguridadRel3Unique
    private Long rel3;

    @SistemaSeguridadRel4Unique
    private Long rel4;

    private Long rel12;

}
