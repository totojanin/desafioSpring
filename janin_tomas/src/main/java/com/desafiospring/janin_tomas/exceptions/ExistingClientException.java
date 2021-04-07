package com.desafiospring.janin_tomas.exceptions;

import lombok.Data;

@Data
public class ExistingClientException extends Exception {
    private final String title = "Cliente ya existente";

    public ExistingClientException(String message) {
        super(message);
    }
}
