package ar.edu.utn.frba.ddsi.climalert.services.impl;

import ar.edu.utn.frba.ddsi.climalert.models.entities.Alerta;
import ar.edu.utn.frba.ddsi.climalert.models.entities.RegistroClima;
import ar.edu.utn.frba.ddsi.climalert.repositories.AlertaRepository;
import ar.edu.utn.frba.ddsi.climalert.repositories.RegistroClimaRepository;
import ar.edu.utn.frba.ddsi.climalert.responses.Messages;
import ar.edu.utn.frba.ddsi.climalert.responses.exceptions.BadRequestException;
import ar.edu.utn.frba.ddsi.climalert.responses.exceptions.NotFoundException;
import ar.edu.utn.frba.ddsi.climalert.services.AlertaService;
import ar.edu.utn.frba.ddsi.climalert.services.EmailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertaServiceImpl implements AlertaService {

    private final RegistroClimaRepository registroClimaRepository;
    private final AlertaRepository alertaRepository;
    private final EmailService emailService;

    public AlertaServiceImpl(
            RegistroClimaRepository registroClimaRepository,
            AlertaRepository alertaRepository,
            EmailService emailService
    ) {
        this.registroClimaRepository = registroClimaRepository;
        this.alertaRepository = alertaRepository;
        this.emailService = emailService;
    }

    @Override
    public Alerta analizarUltimoRegistro() {
        RegistroClima ultimoRegistro = registroClimaRepository.buscarUltimo()
                .orElseThrow(() ->
                        new NotFoundException(Messages.CLIMA_NO_ENCONTRADO.getMessage())
                );

        if (!ultimoRegistro.esCondicionCritica()) {
            throw new BadRequestException(Messages.ALERTA_NO_GENERADA.getMessage());
        }

        return alertaRepository.buscarTodas().stream()
                .filter(alerta -> alerta.getRegistroClima() != null)
                .filter(alerta -> ultimoRegistro.getCodigo().equals(alerta.getRegistroClima().getCodigo()))
                .findFirst()
                .orElseGet(() -> this.generarAlerta(ultimoRegistro));
    }

    private Alerta generarAlerta(RegistroClima ultimoRegistro) {
        Alerta alerta = new Alerta(ultimoRegistro);

        alertaRepository.guardar(alerta);
        emailService.enviarAlerta(alerta);

        return alerta;
    }

    @Override
    public List<Alerta> obtenerTodas() {
        return alertaRepository.buscarTodas();
    }
}
