package com.estafet.taxservice.exception;

/**
 * Created by estafet on 19/10/15.
 */
public class InvalidTaxRequestException extends RuntimeException {
    public InvalidTaxRequestException(String message) {
        super(message);
    }
}
