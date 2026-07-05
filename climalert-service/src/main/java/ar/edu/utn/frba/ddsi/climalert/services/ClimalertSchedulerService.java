package ar.edu.utn.frba.ddsi.climalert.services;

import ar.edu.utn.frba.ddsi.climalert.responses.exceptions.BadRequestException;
import ar.edu.utn.frba.ddsi.climalert.responses.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "climalert.scheduling.enabled", havingValue = "true", matchIfMissing = true)
public class ClimalertSchedulerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClimalertSchedulerService.class);

    private final ClimaService climaService;
    private final AlertaService alertaService;

    public ClimalertSchedulerService(ClimaService climaService, AlertaService alertaService) {
        this.climaService = climaService;
        this.alertaService = alertaService;
    }

    @Scheduled(
            fixedRateString = "${climalert.scheduling.weather.fixed-rate-ms:300000}",
            initialDelayString = "${climalert.scheduling.weather.initial-delay-ms:10000}"
    )
    public void actualizarClima() {
        try {
            climaService.obtenerYGuardarClimaActual();
            LOGGER.info("Registro climático actualizado");
        } catch (Exception exception) {
            LOGGER.error("No se pudo actualizar el clima", exception);
        }
    }

    @Scheduled(
            fixedRateString = "${climalert.scheduling.alert.fixed-rate-ms:60000}",
            initialDelayString = "${climalert.scheduling.alert.initial-delay-ms:20000}"
    )
    public void analizarAlertas() {
        try {
            alertaService.analizarUltimoRegistro();
            LOGGER.info("Análisis de alertas ejecutado");
        } catch (BadRequestException | NotFoundException exception) {
            LOGGER.debug("No se generó alerta: {}", exception.getMessage());
        } catch (Exception exception) {
            LOGGER.error("No se pudo analizar la última medición climática", exception);
        }
    }
}
