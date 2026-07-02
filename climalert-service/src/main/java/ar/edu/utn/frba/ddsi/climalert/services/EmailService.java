package ar.edu.utn.frba.ddsi.climalert.services;

import ar.edu.utn.frba.ddsi.climalert.models.entities.Alerta;

public interface EmailService {
    void enviarAlerta(Alerta alerta);
}