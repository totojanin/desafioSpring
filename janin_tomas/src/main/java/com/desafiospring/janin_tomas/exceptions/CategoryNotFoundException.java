package com.desafiospring.janin_tomas.exceptions;

import lombok.Data;

@Data
public class CategoryNotFoundException extends Exception {
    private final String title = "Categoría no encontrada";

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
