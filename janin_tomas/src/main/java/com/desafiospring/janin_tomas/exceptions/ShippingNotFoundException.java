package com.desafiospring.janin_tomas.exceptions;

import lombok.Data;

@Data
public class ShippingNotFoundException extends Exception {
    private final String title = "Valor de Shipping no encontrado";

    public ShippingNotFoundException(String message) {
        super(message);
    }
}
