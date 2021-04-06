package com.desafiospring.janin_tomas.exceptions;

import lombok.Data;

@Data
public class MaxFiltersException extends Exception {
    private final String title = "Máximo de filtros superado";

    public MaxFiltersException(String message) {
        super(message);
    }
}
