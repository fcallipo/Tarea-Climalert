package ar.edu.utn.frba.ddsi.climalert.models.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Alerta {

    private String codigo;
    private RegistroClima registroClima;
    private String descripcion;
    private LocalDateTime fechaGeneracion;

    public Alerta() {
    }

    public Alerta(
            String codigo,
            RegistroClima registroClima,
            String descripcion,
            LocalDateTime fechaGeneracion
    ) {
        this.codigo = codigo;
        this.registroClima = registroClima;
        this.descripcion = descripcion;
        this.fechaGeneracion = fechaGeneracion;
    }

    public Alerta(RegistroClima registroClima) {
        this.codigo = UUID.randomUUID().toString();
        this.registroClima = registroClima;
        this.descripcion = generarDescripcion(registroClima);
        this.fechaGeneracion = LocalDateTime.now();
    }

    private String generarDescripcion(RegistroClima registroClima) {
        return "Alerta climática: temperatura mayor a 35° y humedad superior a 60%. "
                + "Temperatura actual: " + registroClima.getTemperatura() + "°. "
                + "Humedad actual: " + registroClima.getHumedad() + "%. "
                + "Ubicación: " + registroClima.getUbicacion() + ".";
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public RegistroClima getRegistroClima() {
        return registroClima;
    }

    public void setRegistroClima(RegistroClima registroClima) {
        this.registroClima = registroClima;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDateTime fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }
}
