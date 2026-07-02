package ar.edu.utn.frba.ddsi.climalert.responses.success;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CreatedResponse extends SuccessResponse {

    public CreatedResponse(String message) {
        super(message, HttpStatus.CREATED);
    }

    public static ResponseEntity<CreatedResponse> build(String message) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CreatedResponse(message));
    }
}