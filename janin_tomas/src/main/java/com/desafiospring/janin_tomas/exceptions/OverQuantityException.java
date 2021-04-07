package com.desafiospring.janin_tomas.exceptions;

import lombok.Data;

@Data
public class OverQuantityException extends Exception {
    private final String title = "Cantidad mayor al stock";

    public OverQuantityException(String message) {
        super(message);
    }
}
