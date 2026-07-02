package ar.edu.utn.frba.ddsi.climalert.responses;

public enum Messages {

    CLIMA_NO_ENCONTRADO("No hay registros climáticos cargados"),
    ALERTA_NO_GENERADA("No se generó alerta porque las condiciones climáticas no son críticas"),
    WEATHER_API_ERROR("No se pudo obtener información climática desde WeatherAPI"),
    CLIMA_GUARDADO("El clima actual fue guardado correctamente"),
    ALERTA_GENERADA("La alerta climática fue generada correctamente"),
    CAMPO_REQUERIDO("El campo es obligatorio"),
    FORMATO_INVALIDO("El formato ingresado es inválido");

    private final String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}