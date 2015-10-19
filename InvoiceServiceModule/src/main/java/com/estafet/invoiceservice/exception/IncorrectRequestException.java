package com.estafet.invoiceservice.exception;

/**
 * Created by estafet on 19/10/15.
 */
public class IncorrectRequestException extends RuntimeException {
    public IncorrectRequestException(String message){
        super(message);
    }
}
