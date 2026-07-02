package ar.edu.utn.frba.ddsi.climalert.controllers;

import ar.edu.utn.frba.ddsi.climalert.models.entities.RegistroClima;
import ar.edu.utn.frba.ddsi.climalert.services.impl.ClimaServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clima")
public class ClimaController {

    private final ClimaServiceImpl climaService;

    public ClimaController(ClimaServiceImpl climaService) {
        this.climaService = climaService;
    }

    @GetMapping
    public List<RegistroClima> obtenerHistorial() {
        return climaService.obtenerHistorial();
    }

    @GetMapping("/ultimo")
    public RegistroClima obtenerUltimoRegistro() {
        return climaService.obtenerUltimoRegistro();
    }

    @PostMapping("/actualizar")
    public RegistroClima obtenerYGuardarClimaActual() {
        return climaService.obtenerYGuardarClimaActual();
    }
}