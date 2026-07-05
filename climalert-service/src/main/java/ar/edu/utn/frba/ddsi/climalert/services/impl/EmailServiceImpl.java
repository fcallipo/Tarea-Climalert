package ar.edu.utn.frba.ddsi.climalert.services.impl;

import ar.edu.utn.frba.ddsi.climalert.models.entities.Alerta;
import ar.edu.utn.frba.ddsi.climalert.models.entities.RegistroClima;
import ar.edu.utn.frba.ddsi.climalert.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final ObjectProvider<JavaMailSender> mailSenderProvider;
    private final boolean mailEnabled;
    private final String fromAddress;
    private final List<String> recipients;

    public EmailServiceImpl(
            ObjectProvider<JavaMailSender> mailSenderProvider,
            @Value("${climalert.mail.enabled:false}") boolean mailEnabled,
            @Value("${climalert.mail.from:no-reply@climalert.local}") String fromAddress,
            @Value("${climalert.mail.recipients:admin@clima.com,emergencias@clima.com,meteorologia@clima.com}")
            List<String> recipients
    ) {
        this.mailSenderProvider = mailSenderProvider;
        this.mailEnabled = mailEnabled;
        this.fromAddress = fromAddress;
        this.recipients = recipients;
    }

    @Override
    public void enviarAlerta(Alerta alerta) {
        if (!this.mailEnabled) {
            LOGGER.info("Mail deshabilitado. Alerta generada para destinatarios {}: {}", this.recipients, alerta.getDescripcion());
            return;
        }

        JavaMailSender mailSender = this.mailSenderProvider.getIfAvailable();
        if (mailSender == null) {
            throw new IllegalStateException("No hay un JavaMailSender configurado para enviar alertas");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(this.fromAddress);
        message.setTo(this.recipients.toArray(String[]::new));
        message.setSubject("Climalert - Alerta meteorológica");
        message.setText(this.construirDetalle(alerta));
        mailSender.send(message);
    }

    private String construirDetalle(Alerta alerta) {
        RegistroClima clima = alerta.getRegistroClima();

        return """
                Se generó una alerta climática.

                Codigo alerta: %s
                Fecha alerta: %s
                Ubicación: %s
                Temperatura: %s
                Humedad: %s
                Condición: %s
                Sensación térmica: %s
                Velocidad viento: %s
                Fecha registro: %s

                Descripción:
                %s
                """.formatted(
                alerta.getCodigo(),
                alerta.getFechaGeneracion(),
                clima.getUbicacion(),
                clima.getTemperatura(),
                clima.getHumedad(),
                clima.getCondicion(),
                clima.getSensacionTermica(),
                clima.getVelocidadViento(),
                clima.getFechaRegistro(),
                alerta.getDescripcion()
        );
    }
}
