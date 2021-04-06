package com.desafiospring.janin_tomas.exceptions;

import lombok.Data;

@Data
public class ProductNameNotFoundException extends Exception {
    private final String title = "Producto no encontrado";

    public ProductNameNotFoundException(String message) {
        super(message);
    }
}
