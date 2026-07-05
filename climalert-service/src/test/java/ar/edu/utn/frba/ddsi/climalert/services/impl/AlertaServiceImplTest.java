package ar.edu.utn.frba.ddsi.climalert.services.impl;

import ar.edu.utn.frba.ddsi.climalert.models.entities.Alerta;
import ar.edu.utn.frba.ddsi.climalert.models.entities.RegistroClima;
import ar.edu.utn.frba.ddsi.climalert.repositories.AlertaRepository;
import ar.edu.utn.frba.ddsi.climalert.repositories.RegistroClimaRepository;
import ar.edu.utn.frba.ddsi.climalert.responses.exceptions.BadRequestException;
import ar.edu.utn.frba.ddsi.climalert.responses.exceptions.NotFoundException;
import ar.edu.utn.frba.ddsi.climalert.services.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlertaServiceImplTest {

    @Mock
    private RegistroClimaRepository registroClimaRepository;

    @Mock
    private AlertaRepository alertaRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AlertaServiceImpl alertaService;

    @Test
    void lanzaNotFoundSiNoHayRegistroClimatico() {
        when(registroClimaRepository.buscarUltimo()).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> alertaService.analizarUltimoRegistro());
    }

    @Test
    void lanzaBadRequestSiElRegistroNoEsCritico() {
        RegistroClima registro = new RegistroClima("CABA", 30.0, 55, "Nublado", 29.0, 12.0);
        when(registroClimaRepository.buscarUltimo()).thenReturn(Optional.of(registro));

        assertThrows(BadRequestException.class, () -> alertaService.analizarUltimoRegistro());
    }

    @Test
    void generaAlertaYEnviaMailSiElRegistroEsCritico() {
        RegistroClima registro = new RegistroClima("CABA", 38.0, 70, "Soleado", 41.0, 10.0);
        when(registroClimaRepository.buscarUltimo()).thenReturn(Optional.of(registro));
        when(alertaRepository.buscarTodas()).thenReturn(List.of());

        Alerta alerta = alertaService.analizarUltimoRegistro();

        assertEquals(registro.getCodigo(), alerta.getRegistroClima().getCodigo());
        verify(alertaRepository).guardar(any(Alerta.class));
        verify(emailService).enviarAlerta(any(Alerta.class));
    }

    @Test
    void reutilizaLaAlertaExistenteParaNoDuplicarNotificaciones() {
        RegistroClima registro = new RegistroClima("CABA", 38.0, 70, "Soleado", 41.0, 10.0);
        Alerta alertaExistente = new Alerta(registro);

        when(registroClimaRepository.buscarUltimo()).thenReturn(Optional.of(registro));
        when(alertaRepository.buscarTodas()).thenReturn(List.of(alertaExistente));

        Alerta alerta = alertaService.analizarUltimoRegistro();

        assertSame(alertaExistente, alerta);
        verify(alertaRepository, never()).guardar(any(Alerta.class));
        verify(emailService, never()).enviarAlerta(any(Alerta.class));
    }
}
