package ar.edu.utn.frba.ddsi.climalert.services;

import ar.edu.utn.frba.ddsi.climalert.models.entities.RegistroClima;

import java.util.List;

public interface ClimaService {
    RegistroClima obtenerYGuardarClimaActual();
    List<RegistroClima> obtenerHistorial();
    RegistroClima obtenerUltimoRegistro();
}