package com.prova.airbus.web.api.exception;

public class AirbusNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AirbusNotFoundException(String message) {
        super(message);
    }
}
