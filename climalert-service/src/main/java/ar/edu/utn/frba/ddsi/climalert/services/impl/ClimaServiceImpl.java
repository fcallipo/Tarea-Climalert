package ar.edu.utn.frba.ddsi.climalert.services.impl;

import ar.edu.utn.frba.ddsi.climalert.models.entities.RegistroClima;
import ar.edu.utn.frba.ddsi.climalert.repositories.inMemory.InMemoryRegistroClimaRepository;
import ar.edu.utn.frba.ddsi.climalert.responses.Messages;
import ar.edu.utn.frba.ddsi.climalert.responses.exceptions.NotFoundException;
import ar.edu.utn.frba.ddsi.climalert.services.ClimaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClimaServiceImpl implements ClimaService {

    private final InMemoryRegistroClimaRepository registroClimaRepository;
    private final WeatherServiceImpl weatherService;

    public ClimaServiceImpl(
            InMemoryRegistroClimaRepository registroClimaRepository,
            WeatherServiceImpl weatherService
    ) {
        this.registroClimaRepository = registroClimaRepository;
        this.weatherService = weatherService;
    }

    @Override
    public RegistroClima obtenerYGuardarClimaActual() {
        RegistroClima registro = weatherService.obtenerClimaActual();
        return registroClimaRepository.guardar(registro);
    }

    @Override
    public List<RegistroClima> obtenerHistorial() {
        return registroClimaRepository.buscarTodos();
    }

    @Override
    public RegistroClima obtenerUltimoRegistro() {
        return registroClimaRepository.buscarUltimo()
                .orElseThrow(() ->
                        new NotFoundException(Messages.CLIMA_NO_ENCONTRADO.getMessage())
                );
    }
}