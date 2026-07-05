package ar.edu.utn.frba.ddsi.climalert.models.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class RegistroClima {

    private String codigo;
    private String ubicacion;
    private Double temperatura;
    private Integer humedad;
    private String condicion;
    private Double sensacionTermica;
    private Double velocidadViento;
    private LocalDateTime fechaRegistro;

    public RegistroClima() {
    }

    public RegistroClima(
            String codigo,
            String ubicacion,
            Double temperatura,
            Integer humedad,
            String condicion,
            Double sensacionTermica,
            Double velocidadViento,
            LocalDateTime fechaRegistro
    ) {
        this.codigo = codigo;
        this.ubicacion = ubicacion;
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.condicion = condicion;
        this.sensacionTermica = sensacionTermica;
        this.velocidadViento = velocidadViento;
        this.fechaRegistro = fechaRegistro;
    }

    public RegistroClima(
            String ubicacion,
            Double temperatura,
            Integer humedad,
            String condicion,
            Double sensacionTermica,
            Double velocidadViento
    ) {
        this.codigo = UUID.randomUUID().toString();
        this.ubicacion = ubicacion;
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.condicion = condicion;
        this.sensacionTermica = sensacionTermica;
        this.velocidadViento = velocidadViento;
        this.fechaRegistro = LocalDateTime.now();
    }

    public Boolean esCondicionCritica() {
        return this.temperatura > 35 && this.humedad > 60;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public Integer getHumedad() {
        return humedad;
    }

    public void setHumedad(Integer humedad) {
        this.humedad = humedad;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public Double getSensacionTermica() {
        return sensacionTermica;
    }

    public void setSensacionTermica(Double sensacionTermica) {
        this.sensacionTermica = sensacionTermica;
    }

    public Double getVelocidadViento() {
        return velocidadViento;
    }

    public void setVelocidadViento(Double velocidadViento) {
        this.velocidadViento = velocidadViento;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
