package ar.edu.utn.frba.ddsi.climalert.responses.exceptions;

import org.springframework.http.HttpStatus;

public class UnprocessableEntityException extends AppException {

    public UnprocessableEntityException(String message) {
        super(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}