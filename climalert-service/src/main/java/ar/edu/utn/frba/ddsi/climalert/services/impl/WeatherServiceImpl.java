package ar.edu.utn.frba.ddsi.climalert.services.impl;

import ar.edu.utn.frba.ddsi.climalert.dto.WeatherApiResponseDTO;
import ar.edu.utn.frba.ddsi.climalert.models.entities.RegistroClima;
import ar.edu.utn.frba.ddsi.climalert.services.WeatherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final RestTemplate restTemplate;

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    @Value("${weather.api.key}")
    private String weatherApiKey;

    @Value("${weather.api.location}")
    private String weatherApiLocation;

    public WeatherServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public RegistroClima obtenerClimaActual() {
        String url = weatherApiUrl
                + "?key=" + weatherApiKey
                + "&q=" + weatherApiLocation
                + "&aqi=no";

        WeatherApiResponseDTO response = restTemplate.getForObject(
                url,
                WeatherApiResponseDTO.class
        );

        if (response == null || response.getCurrent() == null || response.getLocation() == null) {
            throw new RuntimeException("No se pudo obtener información climática desde WeatherAPI");
        }

        return new RegistroClima(
                response.getLocation().getName(),
                response.getCurrent().getTempC(),
                response.getCurrent().getHumidity(),
                response.getCurrent().getCondition().getText(),
                response.getCurrent().getFeelsLikeC(),
                response.getCurrent().getWindKph()
        );
    }
}