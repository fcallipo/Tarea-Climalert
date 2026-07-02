package ar.edu.utn.frba.ddsi.climalert.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alerta {

    private String codigo;
    private RegistroClima registroClima;
    private String descripcion;
    private LocalDateTime fechaGeneracion;

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
}