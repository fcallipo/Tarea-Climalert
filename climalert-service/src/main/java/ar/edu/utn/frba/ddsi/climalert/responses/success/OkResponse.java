package ar.edu.utn.frba.ddsi.climalert.responses.success;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class OkResponse extends SuccessResponse {

    public OkResponse(String message) {
        super(message, HttpStatus.OK);
    }

    public static ResponseEntity<OkResponse> build(String message) {
        return ResponseEntity.ok(new OkResponse(message));
    }
}