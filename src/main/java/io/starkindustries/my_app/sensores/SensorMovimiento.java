package io.starkindustries.my_app.sensores;

public class SensorMovimiento extends Sensor {
    public SensorMovimiento(String idSensor, String ubicacion, boolean estado, int sensibilidad) {
        super(idSensor, TipoSensor.Tipo.MOVIMIENTO, ubicacion, estado, sensibilidad);
    }

    @Override
    public void activar() {
        System.out.println("Sensor de movimiento activado.");
    }

    @Override
    public void desactivar() {
        System.out.println("Sensor de movimiento desactivado.");
    }
}
