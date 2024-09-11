package io.starkindustries.my_app.sensores;

public class SensorTemperatura extends Sensor {
    private int temperaturaActual;

    public SensorTemperatura(String idSensor, String ubicacion, boolean estado, int sensibilidad, int temperaturaActual) {
        super(idSensor, TipoSensor.Tipo.TEMPERATURA, ubicacion, estado, sensibilidad);
        this.temperaturaActual = temperaturaActual;
    }

    @Override
    public void activar() {
        System.out.println("Sensor de temperatura activado.");
    }

    @Override
    public void desactivar() {
        System.out.println("Sensor de temperatura desactivado.");
    }

    public int getTemperaturaActual() {
        return temperaturaActual;
    }

    public void setTemperaturaActual(int temperaturaActual) {
        this.temperaturaActual = temperaturaActual;
    }
}
