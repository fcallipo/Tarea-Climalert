package ar.edu.utn.frba.ddsi.climalert.responses.success;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public class SuccessResponse {

    private final int status;
    private final String message;
    private final LocalDateTime timestamp;

    public SuccessResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status.value();
        this.timestamp = LocalDateTime.now();
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
