package com.desafiospring.janin_tomas.exceptions;

import lombok.Data;

@Data
public class BrandNotFoundException extends Exception {
    private final String title = "Categoría no encontrada";

    public BrandNotFoundException(String message) {
        super(message);
    }
}
