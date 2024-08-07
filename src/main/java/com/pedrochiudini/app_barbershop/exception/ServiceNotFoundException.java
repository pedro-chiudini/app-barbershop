package com.pedrochiudini.app_barbershop.exception;

public class ServiceNotFoundException extends RuntimeException {
    
    public ServiceNotFoundException(String message) {
        super(message);
    }

}
