package io.starkindustries.my_app.sensores;

public class SensorAcceso extends Sensor {
    private String accesoPermitido;

    public SensorAcceso(String idSensor, String ubicacion, boolean estado, int sensibilidad, String accesoPermitido) {
        super(idSensor, TipoSensor.Tipo.ACCESO, ubicacion, estado, sensibilidad);
        this.accesoPermitido = accesoPermitido;
    }

    @Override
    public void activar() {
        System.out.println("Sensor de acceso activado.");
    }

    @Override
    public void desactivar() {
        System.out.println("Sensor de acceso desactivado.");
    }

    public String getAccesoPermitido() {
        return accesoPermitido;
    }

    public void setAccesoPermitido(String accesoPermitido) {
        this.accesoPermitido = accesoPermitido;
    }
}

