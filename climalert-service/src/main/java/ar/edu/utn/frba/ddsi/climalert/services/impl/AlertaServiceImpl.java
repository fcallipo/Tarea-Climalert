package ar.edu.utn.frba.ddsi.climalert.services.impl;

import ar.edu.utn.frba.ddsi.climalert.models.entities.Alerta;
import ar.edu.utn.frba.ddsi.climalert.models.entities.RegistroClima;
import ar.edu.utn.frba.ddsi.climalert.repositories.inMemory.InMemoryAlertaRepository;
import ar.edu.utn.frba.ddsi.climalert.repositories.inMemory.InMemoryRegistroClimaRepository;
import ar.edu.utn.frba.ddsi.climalert.responses.Messages;
import ar.edu.utn.frba.ddsi.climalert.responses.exceptions.BadRequestException;
import ar.edu.utn.frba.ddsi.climalert.responses.exceptions.NotFoundException;
import ar.edu.utn.frba.ddsi.climalert.services.AlertaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertaServiceImpl implements AlertaService {

    private final InMemoryRegistroClimaRepository registroClimaRepository;
    private final InMemoryAlertaRepository alertaRepository;
    private final EmailServiceImpl emailService;

    public AlertaServiceImpl(
            InMemoryRegistroClimaRepository registroClimaRepository,
            InMemoryAlertaRepository alertaRepository,
            EmailServiceImpl emailService
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

        if (!ultimoRegistro.esCondicionCritica()) {
            return null;
        }

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