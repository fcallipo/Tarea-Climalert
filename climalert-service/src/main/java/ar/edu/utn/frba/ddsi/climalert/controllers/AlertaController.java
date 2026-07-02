package ar.edu.utn.frba.ddsi.climalert.controllers;

import ar.edu.utn.frba.ddsi.climalert.models.entities.Alerta;
import ar.edu.utn.frba.ddsi.climalert.services.impl.AlertaServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
public class AlertaController {

    private final AlertaServiceImpl alertaService;

    public AlertaController(AlertaServiceImpl alertaService) {
        this.alertaService = alertaService;
    }

    @GetMapping
    public List<Alerta> obtenerTodas() {
        return alertaService.obtenerTodas();
    }

    @PostMapping("/analizar")
    public Alerta analizarUltimoRegistro() {
        return alertaService.analizarUltimoRegistro();
    }
}