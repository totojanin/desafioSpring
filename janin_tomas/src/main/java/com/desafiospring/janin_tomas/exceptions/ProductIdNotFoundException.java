package com.desafiospring.janin_tomas.exceptions;

import lombok.Data;

@Data
public class ProductIdNotFoundException extends Exception {
    private final String title = "ProductId no encontrado";

    public ProductIdNotFoundException(String message) {
        super(message);
    }
}
