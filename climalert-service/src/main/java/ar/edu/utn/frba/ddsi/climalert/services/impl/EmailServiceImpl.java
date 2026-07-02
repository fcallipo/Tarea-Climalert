package ar.edu.utn.frba.ddsi.climalert.services.impl;

import ar.edu.utn.frba.ddsi.climalert.models.entities.Alerta;
import ar.edu.utn.frba.ddsi.climalert.models.entities.RegistroClima;
import ar.edu.utn.frba.ddsi.climalert.services.EmailService;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public void enviarAlerta(Alerta alerta) {
        RegistroClima clima = alerta.getRegistroClima();

        System.out.println("Enviando alerta por correo...");
        System.out.println("Destinatarios:");
        System.out.println("admin@clima.com");
        System.out.println("emergencias@clima.com");
        System.out.println("meteorologia@clima.com");

        System.out.println("Detalle de la alerta:");
        System.out.println("Ubicación: " + clima.getUbicacion());
        System.out.println("Temperatura: " + clima.getTemperatura());
        System.out.println("Humedad: " + clima.getHumedad());
        System.out.println("Condición: " + clima.getCondicion());
        System.out.println("Sensación térmica: " + clima.getSensacionTermica());
        System.out.println("Velocidad viento: " + clima.getVelocidadViento());
        System.out.println("Fecha registro: " + clima.getFechaRegistro());
    }
}