package ar.edu.utn.frba.ddsi.climalert.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherApiResponseDTO {

    private Location location;
    private Current current;

    @Getter
    @Setter
    public static class Location {

        private String name;
        private String region;
        private String country;

        @JsonProperty("localtime")
        private String localtime;
    }

    @Getter
    @Setter
    public static class Current {

        @JsonProperty("temp_c")
        private Double tempC;

        @JsonProperty("feelslike_c")
        private Double feelsLikeC;

        private Integer humidity;

        @JsonProperty("wind_kph")
        private Double windKph;

        private Condition condition;
    }

    @Getter
    @Setter
    public static class Condition {
        private String text;
    }
}