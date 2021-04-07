package com.desafiospring.janin_tomas.exceptions;

import lombok.Data;

@Data
public class DNINotFoundException extends Exception {
    private final String title = "DNI no encontrado";

    public DNINotFoundException(String message) {
        super(message);
    }
}
