package ar.edu.utn.frba.ddsi.climalert.services;

import ar.edu.utn.frba.ddsi.climalert.models.entities.RegistroClima;

public interface WeatherService {
    RegistroClima obtenerClimaActual();
}