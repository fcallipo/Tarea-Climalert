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
public class RegistroClima {

    private String codigo;
    private String ubicacion;
    private Double temperatura;
    private Integer humedad;
    private String condicion;
    private Double sensacionTermica;
    private Double velocidadViento;
    private LocalDateTime fechaRegistro;

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
}