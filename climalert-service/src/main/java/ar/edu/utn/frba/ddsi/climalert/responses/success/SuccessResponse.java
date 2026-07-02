package ar.edu.utn.frba.ddsi.climalert.responses.success;

import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SuccessResponse {

    private final int status;
    private final String message;
    private final LocalDateTime timestamp;

    public SuccessResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status.value();
        this.timestamp = LocalDateTime.now();
    }
}