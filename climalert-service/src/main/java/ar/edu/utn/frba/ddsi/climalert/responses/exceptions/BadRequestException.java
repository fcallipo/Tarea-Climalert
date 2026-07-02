package ar.edu.utn.frba.ddsi.climalert.responses.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends AppException {

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}