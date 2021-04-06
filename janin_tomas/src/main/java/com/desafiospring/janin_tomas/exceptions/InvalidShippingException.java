package com.desafiospring.janin_tomas.exceptions;

import lombok.Data;

@Data
public class InvalidShippingException extends Exception {
    private final String title = "Valor de Shipping incorrecto";

    public InvalidShippingException(String message) {
        super(message);
    }
}
