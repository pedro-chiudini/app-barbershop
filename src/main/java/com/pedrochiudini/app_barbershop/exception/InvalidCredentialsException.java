package com.pedrochiudini.app_barbershop.exception;

public class InvalidCredentialsException extends RuntimeException {
    
    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }

}
