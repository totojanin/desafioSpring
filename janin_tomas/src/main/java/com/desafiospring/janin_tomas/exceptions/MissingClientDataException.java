package com.desafiospring.janin_tomas.exceptions;

import lombok.Data;

@Data
public class MissingClientDataException extends Exception {
    private final String title = "Faltan datos";

    public MissingClientDataException(String message) {
        super(message);
    }
}