package ar.edu.utn.frba.ddsi.climalert.services;

import ar.edu.utn.frba.ddsi.climalert.models.entities.Alerta;

import java.util.List;

public interface AlertaService {
    Alerta analizarUltimoRegistro();
    List<Alerta> obtenerTodas();
}