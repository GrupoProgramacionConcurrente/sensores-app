package io.starkindustries.my_app.sensores;

public abstract class Sensor {
    private String idSensor;
    private TipoSensor.Tipo tipo;  // Usa el enum aquí
    private String ubicacion;
    private boolean estado;
    private int sensibilidad;

    // Constructor
    public Sensor(String idSensor, TipoSensor.Tipo tipo, String ubicacion, boolean estado, int sensibilidad) {
        this.idSensor = idSensor;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.estado = estado;
        this.sensibilidad = sensibilidad;
    }

    // Métodos getters y setters
    public String getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(String idSensor) {
        this.idSensor = idSensor;
    }

    public TipoSensor.Tipo getTipo() {
        return tipo;
    }

    public void setTipo(TipoSensor.Tipo tipo) {
        this.tipo = tipo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getSensibilidad() {
        return sensibilidad;
    }

    public void setSensibilidad(int sensibilidad) {
        this.sensibilidad = sensibilidad;
    }

    // Método abstracto que las subclases deben implementar
    public abstract void activar();

    // Método abstracto que las subclases deben implementar
    public abstract void desactivar();
}

